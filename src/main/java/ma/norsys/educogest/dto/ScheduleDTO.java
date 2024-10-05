package ma.norsys.educogest.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import ma.norsys.educogest.models.DaySchedule;
import ma.norsys.educogest.models.School;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleDTO {

    private Long id;

    private School school;

    private List<DayScheduleDTO> daySchedules;
}
