package com.example.mongo_db.Entity.Items.Types.Clothes.Underwear.Underpants;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Size;
import lombok.Data;

@Data

public class Underpants extends Item {
    private String color;
    private Gender gender;
    private Size size;

}
