package com.example.mongo_db.Repository.ProducerRepoes;

import com.example.mongo_db.Entity.Producer.Producer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProducerRepo extends MongoRepository<Producer, String> {


}
