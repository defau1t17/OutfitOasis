package com.example.mongo_db.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.util.UUID;

@Data
@Document(collection = "employee")
public abstract class User {


    @Id
    private UUID id;

    private String name;

    private String second_name;

    private int age;

    private Gender gender;
}

