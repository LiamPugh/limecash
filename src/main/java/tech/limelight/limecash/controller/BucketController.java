package tech.limelight.limecash.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Bucket;
import tech.limelight.limecash.repository.BucketRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BucketController {

    private BucketRepository bucketRepository;

    public BucketController(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    @GetMapping("/getAllBuckets")
    public List<Bucket> getAllBuckets() {
        return bucketRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }


}
