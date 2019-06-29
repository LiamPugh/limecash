package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
