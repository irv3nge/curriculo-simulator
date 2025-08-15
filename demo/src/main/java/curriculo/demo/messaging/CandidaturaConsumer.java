package curriculo.demo.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import curriculo.demo.model.CandidaturaMessage;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CandidaturaConsumer {

    private final JavaMailSender mailSender;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${app.email.to}") private String destino;

    public CandidaturaConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void onMessage(String json) {
        try {
            CandidaturaMessage m = mapper.readValue(json, CandidaturaMessage.class);
            enviarEmail(m);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: log e DLQ
        }
    }

    private void enviarEmail(CandidaturaMessage m) throws MessagingException {
        var mime = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mime, true, "UTF-8");
        helper.setTo(destino);
        helper.setSubject("Nova candidatura: " + m.getNomeCompleto());
        helper.setText("""
                ID: %d
                Nome: %s
                E-mail: %s
                Celular: %s
                LinkedIn: %s
                Pretensão: %s
                Tipo: %s
                """.formatted(
                m.getId(), m.getNomeCompleto(), m.getEmail(), m.getCelular(),
                m.getLinkedin(), m.getPretensaoSalarial(), m.getTipoContrato()
        ), false);

        var file = new FileSystemResource(new File(m.getPdfPath()));
        helper.addAttachment(m.getOriginalFilename() == null ? "curriculo.pdf" : m.getOriginalFilename(), file);

        mailSender.send(mime);
    }
}
