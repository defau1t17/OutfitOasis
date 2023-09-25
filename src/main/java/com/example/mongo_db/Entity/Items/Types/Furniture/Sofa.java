package com.example.mongo_db.Entity.Items.Types.Furniture;

import com.example.mongo_db.Entity.Items.models.Item;
import com.example.mongo_db.Entity.Items.models.ShopItem;
import lombok.Data;

@Data
public class Sofa extends Item {
    private String color;
    private int width;
    private int depth;
    private int height;
    private int weight;

}
