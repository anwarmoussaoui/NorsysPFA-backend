package ma.norsys.educogest.dto;

import lombok.*;
import ma.norsys.educogest.models.Parent;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParentDTO {
    private Long id;

    private String name;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String occupation;
    private String emailAddress;
}