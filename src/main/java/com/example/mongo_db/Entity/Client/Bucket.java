package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "bucket")
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {

    @Id
    private String id;
    @Field(name = "items")
    private ArrayList<ClientShopItemDAO> items;


}
