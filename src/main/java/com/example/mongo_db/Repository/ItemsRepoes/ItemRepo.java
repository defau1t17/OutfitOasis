package com.example.mongo_db.Repository.ItemsRepoes;

import com.example.mongo_db.Entity.Items.Item.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends MongoRepository<ShopItem, String> {


    List<ShopItem> findAllByItem_Category(String category);

    Page<ShopItem> findAll(Pageable pageable);

    List<ShopItem> findAll();

    Optional<ShopItem> findShopItemById(String id);

}
