package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    Page<School> findAll(Pageable pageable);
}