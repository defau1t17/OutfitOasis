package com.example.mongo_db.Entity.Producer;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Data
@Document(collection = "producers")
@Builder
public class Producer {
    @Id
    private String id;
    @Field(name = "brand")
    private String brandName;
    @Field(name = "country")
    private String country;
    @Field(name = "producedItems")
    private ArrayList<ShopItem> producedItems;
    @Field("email")
    private String producerEmail;
    @Field("root")
    private Client client;


}
