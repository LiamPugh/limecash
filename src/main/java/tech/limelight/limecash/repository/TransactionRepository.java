package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
