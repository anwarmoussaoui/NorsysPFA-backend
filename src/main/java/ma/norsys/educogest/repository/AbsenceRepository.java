package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Absence;
import ma.norsys.educogest.models.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    Page<Absence> findAll(Pageable pageable);
}
