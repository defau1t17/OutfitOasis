package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.models.Item;
import com.example.mongo_db.Entity.Items.models.ShopItem;
import com.example.mongo_db.Entity.Items.models.Size;
import lombok.Data;


@Data
public class Tshirt extends Item {

    private String color;

    private Size size;

    private int height;

    private Gender gender;

}
