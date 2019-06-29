package tech.limelight.limecash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Bucket;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
