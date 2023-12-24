package com.example.mongo_db.Entity.Items.Item;

import com.example.mongo_db.Entity.Producer.Producer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "shop_items")
public class ShopItem {

    @Id
    private String id;
    @NotNull
    private Item item;

    private String producer_id;

    //private List<Image> images;

}
