package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@Document(collection = "bucket")
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {

    @Id
    private String id;

    private HashMap<ShopItem, Integer> items;


}
