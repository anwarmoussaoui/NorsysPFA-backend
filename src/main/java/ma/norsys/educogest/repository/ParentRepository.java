package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Parent;
import ma.norsys.educogest.models.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>  {

    Page<Parent> findAll(Pageable pageable);
}
