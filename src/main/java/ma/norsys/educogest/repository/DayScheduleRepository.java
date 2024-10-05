package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.DaySchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule,Long> {
    Page<DaySchedule> findAll(Pageable pageable);


}
