package com.example.mongo_db.Entity.Items.Types.Clothes.Outwear.Jacket;

import com.example.mongo_db.Entity.Items.Types.Clothes.Clothes;
import com.example.mongo_db.Entity.Items.models.Season;
import lombok.Data;

@Data

public class Jacket extends Clothes {
    private Season season;
}
