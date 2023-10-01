package com.example.mongo_db.Entity.Items.Types.Clothes;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Season;
import com.example.mongo_db.Entity.Items.models.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Data
public abstract class Clothes extends Item {
    private String color;
    private Size size;
    private HashMap<Composition, Integer> clothes_composition;
    private Gender gender;
    private int height;
}
