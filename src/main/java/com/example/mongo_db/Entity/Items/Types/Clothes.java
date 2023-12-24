package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Composition;
import lombok.Data;

@Data
public class Clothes extends Item {
    private Image item_image;
    private Composition composition;

}
