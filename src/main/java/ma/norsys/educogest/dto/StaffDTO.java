package ma.norsys.educogest.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StaffDTO {
    private Long id;
    private String name;
    private String firstName;
    private String position;
}


