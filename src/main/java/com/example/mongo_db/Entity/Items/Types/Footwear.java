package com.example.mongo_db.Entity.Items.Types;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Items.Item.Item;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Footwear extends Item {
    private Image item_image;
}
