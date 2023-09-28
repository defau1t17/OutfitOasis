package com.example.mongo_db.Entity.Client;


import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;

    private String fileName;
    private String fileType;

}
