package com.example.mongo_db.Repository.ClientsRepoes;

import com.example.mongo_db.Entity.Client.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepo extends MongoRepository<Client, String> {

}
