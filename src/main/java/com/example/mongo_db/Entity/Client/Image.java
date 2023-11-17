package com.example.mongo_db.Entity.Client;


import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
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
    private String image;

    public Image update(Image oldImage) {
        if (oldImage != null) {
            this.image = oldImage.getImage();
        }
        return this;
    }


}
