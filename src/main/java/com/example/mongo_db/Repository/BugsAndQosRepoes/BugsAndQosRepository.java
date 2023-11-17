package com.example.mongo_db.Repository.BugsAndQosRepoes;

import com.example.mongo_db.Entity.BugsAndQos.BugsAndQOS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BugsAndQosRepository extends MongoRepository<BugsAndQOS, String> {

    Optional<BugsAndQOS> findByRequestId(String requestID);

}
