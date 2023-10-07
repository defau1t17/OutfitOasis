package com.example.mongo_db.Service.Bucket;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Repository.ClientsRepoes.BucketRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BucketService implements EntityOperations {

    @Autowired
    private BucketRepo bucketRepo;


    @Override
    public void save_entity(Object obj) {
        bucketRepo.save((Bucket) obj);
    }

    @Override
    public void update_entity(Object obj) {
        bucketRepo.save((Bucket) obj);
    }

    @Override
    public void remove_entity(Object obj) {
        bucketRepo.delete((Bucket) obj);
    }
}
