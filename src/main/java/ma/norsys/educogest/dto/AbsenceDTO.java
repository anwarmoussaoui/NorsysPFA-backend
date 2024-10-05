package ma.norsys.educogest.dto;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDTO {
    private long id;
    @Temporal(TemporalType.DATE)
    private Date absenceDate;
    private String reason;
    private boolean justified;
    private int numberOfDays;
    private boolean notified;
    private String notes;

}
