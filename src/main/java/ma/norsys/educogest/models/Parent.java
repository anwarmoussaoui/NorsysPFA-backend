package ma.norsys.educogest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String occupation;
    private String emailAddress;
    @JsonIgnoreProperties("parent")
    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)

    private List<Student> children;
}
