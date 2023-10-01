package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.FootComposition;
import com.example.mongo_db.Entity.Items.models.Season;
import lombok.Data;

import java.util.HashMap;
@Data

public abstract class Footwear extends Item {
    private HashMap<Integer, Integer> foot_size;
    private String color;
    private Gender gender;
    private Season season;
    private FootComposition composition;

}
