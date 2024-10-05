package ma.norsys.educogest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.norsys.educogest.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    Page<Schedule> findAll(Pageable pageable);
   /*
    @PersistenceContext
       private EntityManager entityManager;

    @Transactional
    public default void insertWithQuery(Schedule schedule) {
        entityManager.createNativeQuery("INSERT INTO schedule (id, DaySchedule) VALUES (?,?)")
                .setParameter(1, schedule.getId())
                .setParameter(2, schedule.getDaySchedules())
                .executeUpdate();
    } */



}
