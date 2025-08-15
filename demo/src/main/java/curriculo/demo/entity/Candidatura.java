package curriculo.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity @Table(name = "candidaturas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Candidatura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String email;
    private String celular;
    private String linkedin;
    private String pretensaoSalarial;
    private String tipoContrato; // CLT/Estagio
    private String pdfPath;      // caminho salvo no disco
    private String originalFilename;

    private OffsetDateTime criadoEm;
}