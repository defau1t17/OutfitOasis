package com.example.mongo_db.Repository.RequestsRepost;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface RequestsRepo extends MongoRepository<GlobalRequests, String> {

    @Query("{'request_sender' :  ?0 }")
    Optional<GlobalRequests> findByRequest_sender(Client client);


}
