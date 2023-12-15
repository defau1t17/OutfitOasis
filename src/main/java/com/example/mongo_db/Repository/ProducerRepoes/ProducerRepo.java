package com.example.mongo_db.Repository.ProducerRepoes;

import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Producer.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepo extends MongoRepository<Producer, String> {

    @Override
    Optional<Producer> findById(String id);


    @Query(value = "{'id' :  ?0, }", fields = "{'producedItems': 1}")
    Page<ShopItem> findAllShopItemsByProducerID(String producerID, Pageable Pageable);


}
