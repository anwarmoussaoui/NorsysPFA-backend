package ma.norsys.educogest.models;
 
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String address;
    @Column(unique = true)
    private String identifier;
    private String level;
    private String educationalPath;
    private String healthCondition;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties("children")
    private Parent parent;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Absence> absences;
    public void addAbsence(Absence absence) {
        absences.add(absence);}
}