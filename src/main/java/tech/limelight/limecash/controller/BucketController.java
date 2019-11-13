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

    private static BucketRepository bucketRepository;

    public BucketController(BucketRepository bucketRepository) {
        BucketController.bucketRepository = bucketRepository;
    }

    @GetMapping("/getAllBuckets")
    public List<Bucket> getAllBuckets() {
        return bucketRepository.findAll().stream().filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    @GetMapping("/getBucketIdFromName")
    public static Long getBucketIdFromName(String name) {
        try {
            return bucketRepository.findAll().stream()
                    .filter(a -> a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                    .filter(a -> a.getName().equals(name)).collect(Collectors.toList()).get(0).getId();
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Couldn't find Bucket with name " + name);
        }
    }

    private void transferBetweenBuckets(Long from, Long to, Double value) {
        Bucket fromBucket = bucketRepository.getOne(from);
        fromBucket.setValue(fromBucket.getValue() - value);
        Bucket toBucket = bucketRepository.getOne(to);
        toBucket.setValue(toBucket.getValue() + value);
        bucketRepository.save(fromBucket);
        bucketRepository.save(toBucket);
    }

    public void takeFromBucket(String from, Double value){
        Long idFrom = getBucketIdFromName(from);
        takeFromBucket(idFrom,value);
    }

    void takeFromBucket(Long idFrom, Double value){
        Bucket fromBucket = bucketRepository.getOne(idFrom);
        fromBucket.setValue(fromBucket.getValue() - value);
        bucketRepository.save(fromBucket);
    }

    void transferBetweenBuckets(String from, String to, Double value) {
        Long idFrom = getBucketIdFromName(from);
        Long idTo = getBucketIdFromName(to);
        transferBetweenBuckets(idFrom, idTo, value);
    }

}
