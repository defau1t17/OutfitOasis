package com.example.mongo_db.Entity.Client;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "images")
@Data
public class Image {

    @Id
    private String id;

    private Client client;

    private String image_name;
}
