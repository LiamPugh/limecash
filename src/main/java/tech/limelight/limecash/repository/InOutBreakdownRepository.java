package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.InOutBreakdown;

public interface InOutBreakdownRepository extends JpaRepository<InOutBreakdown, Long> {
}
