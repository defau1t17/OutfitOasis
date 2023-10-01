package com.example.mongo_db.Entity.Items.Item;

import com.example.mongo_db.Entity.Items.Item.Item;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "shop_items")
public abstract class ShopItem {

    @Id
    private String id;

    private Item item;

}
