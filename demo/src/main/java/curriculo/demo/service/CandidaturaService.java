package curriculo.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import curriculo.demo.entity.Candidatura;
import curriculo.demo.model.CandidaturaMessage;
import curriculo.demo.repo.CandidaturaRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.time.OffsetDateTime;

@Service
public class CandidaturaService {

    private final CandidaturaRepository repo;
    private final AmqpTemplate amqp;
    private final ObjectMapper mapper = new ObjectMapper();

    private final Path uploadDir;

    public CandidaturaService(
            CandidaturaRepository repo,
            AmqpTemplate amqp,
            @Value("${app.storage.dir}") String storageDir
    ) {
        this.repo = repo;
        this.amqp = amqp;
        this.uploadDir = Paths.get(storageDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar a pasta de uploads: " + this.uploadDir, e);
        }
    }

    @Value("${rabbitmq.exchange}") private String exchange;
    @Value("${rabbitmq.routing-key}") private String routingKey;

    public CandidaturaMessage receber(
            String nome, String email, String celular, String linkedin,
            String pretensao, String tipoContrato, MultipartFile pdf
    ) {
        try {
            if (pdf.isEmpty()) throw new IllegalArgumentException("Arquivo vazio.");
            String original = StringUtils.cleanPath(pdf.getOriginalFilename());
            String safeName = System.currentTimeMillis() + "-" + original;
            Path dest = uploadDir.resolve(safeName).normalize();

            try (InputStream in = pdf.getInputStream()) {
                Files.copy(in, dest, StandardCopyOption.REPLACE_EXISTING);
            }

            Candidatura cand = Candidatura.builder()
                    .nomeCompleto(nome).email(email).celular(celular)
                    .linkedin(linkedin).pretensaoSalarial(pretensao).tipoContrato(tipoContrato)
                    .pdfPath(dest.toString()).originalFilename(original)
                    .criadoEm(OffsetDateTime.now())
                    .build();
            cand = repo.save(cand);

            CandidaturaMessage msg = CandidaturaMessage.builder()
                    .id(cand.getId())
                    .nomeCompleto(nome).email(email).celular(celular).linkedin(linkedin)
                    .pretensaoSalarial(pretensao).tipoContrato(tipoContrato)
                    .pdfPath(cand.getPdfPath()).originalFilename(cand.getOriginalFilename())
                    .build();

            amqp.convertAndSend(exchange, routingKey, mapper.writeValueAsString(msg));
            return msg;

        } catch (Exception e) {
            throw new RuntimeException("Falha ao processar candidatura", e);
        }
    }
}
