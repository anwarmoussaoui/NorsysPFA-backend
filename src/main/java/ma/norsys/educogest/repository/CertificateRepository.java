package ma.norsys.educogest.repository;

import ma.norsys.educogest.models.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Page<Certificate> findAll(Pageable pageable);
}
