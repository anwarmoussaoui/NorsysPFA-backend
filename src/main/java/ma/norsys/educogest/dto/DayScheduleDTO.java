package ma.norsys.educogest.dto;

import lombok.*;
import ma.norsys.educogest.models.Session;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DayScheduleDTO {
    private Long id;

    private String dayOfWeek;
    private List<Session> sessions;
}
