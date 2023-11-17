package com.example.mongo_db.Repository.LogData;

import com.example.mongo_db.Entity.Logger.LogData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogDataRepo extends MongoRepository<LogData, String> {
    Optional<LogData> findByClientID(String client_id);

}
