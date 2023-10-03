package com.example.mongo_db.Service.Bucket;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Repository.ClientsRepoes.BucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    @Autowired
    private BucketRepo bucketRepo;


    public void updateBucket(Bucket bucket) {
        bucketRepo.save(bucket);
    }

}
