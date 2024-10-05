package ma.norsys.educogest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "student_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String groupCode;
    private String groupName;
    private String groupEmail;
    private String groupDescription;
    private boolean isSpecialGroup;
    private boolean isActive;
    private int maxCapacity;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Student> students;
    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
