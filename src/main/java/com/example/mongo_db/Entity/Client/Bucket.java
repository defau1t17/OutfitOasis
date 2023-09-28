package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.Items.models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@Document(collection = "bucket")
@NoArgsConstructor
@AllArgsConstructor
public class Bucket  {

    @Id
    private String id;

    private HashMap<Item, Integer> items;


}
