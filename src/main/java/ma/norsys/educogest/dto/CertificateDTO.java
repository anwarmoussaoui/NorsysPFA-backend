package ma.norsys.educogest.dto;

import lombok.*;
import ma.norsys.educogest.models.Student;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CertificateDTO {
    private Long id;
    private Student student;
    private Date certificateGeneration;
    private Date printing;
    private String format;
}
