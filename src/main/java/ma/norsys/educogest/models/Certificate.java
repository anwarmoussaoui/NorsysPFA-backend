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
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date certificateGeneration;
    @Temporal(TemporalType.DATE)
    private Date printing;
    private String format;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
