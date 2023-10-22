package com.example.mongo_db.Entity.BugsAndQos;


import com.example.mongo_db.Entity.Requests.GlobalRequests;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "problems")
public class BugsAndQOS {

    @Id
    private String id;

    private GlobalRequests request;
}
