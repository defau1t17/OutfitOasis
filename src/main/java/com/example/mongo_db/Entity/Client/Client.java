package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "clients")
public class Client extends User {

    @Id
    private String id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String mail;

    @NotNull
    @NotBlank
    @NotEmpty
    private String phone_number;

    @NotNull
    @NotBlank
    @NotEmpty
    private String client_user_name;

    @NotNull
    @NotBlank
    @NotEmpty
    private String client_password;

    @Field(name = "client_address")
    private Address address;

    @Field(name = "client_bucket")
    private Bucket bucket;

    @Field(name = "client_image")
    private Image image;


}
