package com.example.mongo_db.Entity.Items.Types.Clothes.Outwear.Coat;

import com.example.mongo_db.Entity.Items.Types.Clothes.Clothes;
import com.example.mongo_db.Entity.Items.models.Season;
import lombok.Data;

import java.util.HashMap;
@Data

public class Coat extends Clothes {
    // max and min temp
    private HashMap<Integer, Integer> temperature;
    private Season season;
}
