package com.example.mongo_db.Entity.Logger;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "loggs")
@Data
public class LogData {
    @Id
    private String id;

    private String clientID;

    private ArrayList<String> log = new ArrayList<>();
}
