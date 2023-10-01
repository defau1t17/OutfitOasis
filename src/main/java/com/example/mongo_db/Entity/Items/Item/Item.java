package com.example.mongo_db.Entity.Items.Item;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Items.models.Category;
import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class Item {

    private Category category;

    private String composition;

    private Long price;

    private String description;

    private String country_producer;

    private String producer;

//    private ArrayList<Image> image;
}
