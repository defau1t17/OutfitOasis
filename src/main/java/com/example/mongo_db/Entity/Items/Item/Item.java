package com.example.mongo_db.Entity.Items.Item;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Season;
import com.example.mongo_db.Entity.Items.models.Size;
import lombok.Data;

@Data
public abstract class Item {

    private String name;

    private Category category;

    private long price;

    private String description;

    private Size size;

    private Season season;

    private Gender gender;

    private Composition composition;

}
