package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Schedule;
import ma.norsys.educogest.models.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    Page<Session> findAll(Pageable pageable);
}
