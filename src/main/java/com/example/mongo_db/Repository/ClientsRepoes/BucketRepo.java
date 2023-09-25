package com.example.mongo_db.Repository.ClientsRepoes;

import com.example.mongo_db.Entity.Client.Bucket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepo extends MongoRepository<Bucket, String> {

}
