package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Income;

public interface IncomeRepository extends JpaRepository<Income,Long> {
}
