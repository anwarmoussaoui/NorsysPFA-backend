package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Page<Staff> findAll(Pageable pageable);
}