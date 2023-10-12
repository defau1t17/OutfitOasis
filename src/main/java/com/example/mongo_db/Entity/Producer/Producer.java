package com.example.mongo_db.Entity.Producer;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@Document(collection = "producers")
public class Producer {
    @Id
    private String id;
    @Field(name = "brand_name")
    private String producer_brand_name;
    @Field(name = "country")
    private String producer_country;
    @Field(name = "produced_items")
    private ArrayList<ShopItem> producer_items;

    private Client client;


}
