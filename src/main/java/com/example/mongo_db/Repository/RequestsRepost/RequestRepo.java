package com.example.mongo_db.Repository.RequestsRepost;

import com.example.mongo_db.Entity.Requests.GlobalRequests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends MongoRepository<GlobalRequests, String> {
}
