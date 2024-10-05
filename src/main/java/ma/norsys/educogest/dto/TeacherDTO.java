package ma.norsys.educogest.dto;

import lombok.*;
import ma.norsys.educogest.models.Schedule;
import ma.norsys.educogest.models.Subject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherDTO {
    private long id;
    private String name;
    private String firstName;
    private String photo;
    private Subject subjectTaught;
    private Schedule schedule;
}
