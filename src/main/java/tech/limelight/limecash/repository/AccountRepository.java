package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
