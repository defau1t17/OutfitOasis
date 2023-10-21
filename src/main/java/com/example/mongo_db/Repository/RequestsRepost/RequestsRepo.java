package com.example.mongo_db.Repository.RequestsRepost;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface RequestsRepo extends MongoRepository<GlobalRequests, String> {

    @Query("{'request_sender.id' :  ?0 , 'tag' : ?1 }")
    Optional<GlobalRequests> isClientInRequestList(String id, RequestTags tags);


    Page<GlobalRequests> getRequestByPage(Pageable pageable);


}
