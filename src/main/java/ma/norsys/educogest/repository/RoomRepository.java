package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface RoomRepository extends JpaRepository<Room,Long> {
    Page<Room> findAll(Pageable pageable);
}
