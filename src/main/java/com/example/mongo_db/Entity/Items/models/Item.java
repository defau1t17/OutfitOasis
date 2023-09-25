package com.example.mongo_db.Entity.Items.models;

import lombok.Data;

@Data
public abstract class Item {

    private Category category;

    private String composition;

    private Long price;

    private String description;

    private String producer;
}
