package com.example.mongo_db.Repository.ClientsRepoes;

import com.example.mongo_db.Entity.Client.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepo extends MongoRepository<Image, String> {

}
