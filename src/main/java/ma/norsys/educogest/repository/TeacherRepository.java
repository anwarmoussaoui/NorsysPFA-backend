package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Parent;
import ma.norsys.educogest.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Page<Teacher> findAll(Pageable pageable);
}
