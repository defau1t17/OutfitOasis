package com.example.mongo_db.Entity.Items.Item;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Producer.Producer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
public abstract class Item<T> {

    private String name;

    private Category category;

    private long price;

    private String description;

    private String country_producer;

    private T producer;

//    private ArrayList<Image> image;
}
