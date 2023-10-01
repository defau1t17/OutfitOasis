package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Season;
import com.example.mongo_db.Entity.Items.models.Size;

import java.util.HashMap;

public abstract class Clothes extends Item {
    private String color;
    private Size size;
    private HashMap<Composition, Integer> composition;
    private Gender gender;
    private int height;
}
