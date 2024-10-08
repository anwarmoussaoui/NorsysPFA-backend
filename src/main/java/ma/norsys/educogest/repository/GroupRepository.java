package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Page<Group> findAll(Pageable pageable);
}
