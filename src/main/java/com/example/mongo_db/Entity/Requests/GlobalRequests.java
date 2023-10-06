package com.example.mongo_db.Entity.Requests;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "requests")
public class GlobalRequests<T> {

    @Id
    private String id;

    private RequestTags tag;

    private T data_inf;

    private Client request_sender;

}
