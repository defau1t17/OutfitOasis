package com.example.mongo_db.Service.Items;


import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Items.Types.Clothes.Dress.Dress;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Size;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatalogService {

    @Autowired
    private ItemRepo itemRepo;

    public List<Category> findAllCategories() {
        return Arrays.asList(Category.values());
    }

    public List<ShopItem> findAllItems() {
        return itemRepo.findAll();
    }

    public List<ShopItem> findAllItemsByCategory(String category) {
        return itemRepo.findAllByItem_Category(category);
    }

    public ShopItem findItemById(String id) {
        return itemRepo.findShopItemById(id).orElse(null);

    }


}
