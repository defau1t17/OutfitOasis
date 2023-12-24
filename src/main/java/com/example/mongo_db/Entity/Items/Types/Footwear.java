package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.FootComposition;
import lombok.Data;

@Data
public class Footwear extends Item {
    private Image item_image;
    private FootComposition composition;
}
