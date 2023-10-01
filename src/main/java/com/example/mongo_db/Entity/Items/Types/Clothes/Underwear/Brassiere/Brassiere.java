package com.example.mongo_db.Entity.Items.Types.Clothes.Underwear.Brassiere;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Size;
import lombok.Data;

@Data

public class Brassiere extends Item {
    private String color;
    private final Gender gender = Gender.Woman;
    private Size size;

}
