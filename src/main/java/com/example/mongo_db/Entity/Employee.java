package com.example.mongo_db.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee extends User {

    private int experience;

    private Rank rank;

    private Post post;

}
