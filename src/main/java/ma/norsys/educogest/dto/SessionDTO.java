package ma.norsys.educogest.dto;

import jakarta.persistence.*;
import lombok.*;
import ma.norsys.educogest.models.Room;
import ma.norsys.educogest.models.Subject;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionDTO {

    private Long id;

    private Date startTime;

    private Date endTime;

    private Room room;

    private Subject subject;
}