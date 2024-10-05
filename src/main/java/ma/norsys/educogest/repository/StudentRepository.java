package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Student;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);
    List<Student> findByIdentifierContainingIgnoreCaseOrNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String identifier,
            String name,
            String firstName,
            String address,
            Pageable pageable
    );

    Student findByIdentifier(String identifier);

}