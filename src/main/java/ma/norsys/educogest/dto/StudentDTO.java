package ma.norsys.educogest.dto;

import lombok.*;
import ma.norsys.educogest.models.Absence;
import ma.norsys.educogest.models.Parent;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentDTO {
    private Long id;
    private Boolean isActive;
    private String name;
    private String firstName;
    private Date dateOfBirth;
    private String address;
    private String identifier;
    private String level;
    private String studentClass;
    private String educationalPath;
    private String healthCondition;
    private Parent parent;
    private List<Absence> absences;
}