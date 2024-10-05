package ma.norsys.educogest.models;
 
import jakarta.persistence.*;
import lombok.*;
import ma.norsys.educogest.enumeration.ERole;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ERole name;
}
