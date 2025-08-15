package curriculo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidaturaMessage {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String celular;
    private String linkedin;
    private String pretensaoSalarial;
    private String tipoContrato;
    private String pdfPath;
    private String originalFilename;
}