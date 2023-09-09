package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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


}
