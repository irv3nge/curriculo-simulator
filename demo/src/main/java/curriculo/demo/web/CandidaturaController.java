package curriculo.demo.web;


import curriculo.demo.model.CandidaturaMessage;
import curriculo.demo.service.CandidaturaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {

    private final CandidaturaService service;

    public CandidaturaController(CandidaturaService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CandidaturaMessage> enviar(
            @RequestParam String nomeCompleto,
            @RequestParam String email,
            @RequestParam String celular,
            @RequestParam String linkedin,
            @RequestParam String pretensaoSalarial,
            @RequestParam String tipoContrato,
            @RequestParam("curriculoPdf") MultipartFile curriculoPdf // <== o name deve bater
    ) {
        var msg = service.receber(nomeCompleto, email, celular, linkedin, pretensaoSalarial, tipoContrato, curriculoPdf);
        return ResponseEntity.accepted().body(msg);
    }
}