package com.example.mongo_db.Repository.ProducerRepoes;

import com.example.mongo_db.Entity.Producer.Producer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepo extends MongoRepository<Producer, String> {

    @Override
    Optional<Producer> findById(String id);
}
