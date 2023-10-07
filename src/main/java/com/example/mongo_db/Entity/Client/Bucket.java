package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.DAO.ClientShopItemDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@Document(collection = "bucket")
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {

    @Id
    private String id;
    @Field(name = "items")
    private ArrayList<ClientShopItemDAO> client_items;


}
