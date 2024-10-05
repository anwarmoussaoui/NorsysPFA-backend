package ma.norsys.educogest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date absenceDate;
    private String reason; // Attribute for the reason of the absence
    private boolean justified;
    private int numberOfDays; // Attribute for the number of days of absence
    private boolean notified; // Attribute to indicate if the absence has been notified to the school/teacher
    private String notes; // Additional notes or comments related to the absence
}
