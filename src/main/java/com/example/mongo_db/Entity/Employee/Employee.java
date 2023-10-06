package com.example.mongo_db.Entity.Employee;

import com.example.mongo_db.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee extends User {

    @Id
    private String id;

    @NotNull
    private int experience;

    @NotNull
    private Rank rank;
    @NotNull
    private Post post;
    @NotEmpty
    @NotNull
    @NotBlank
    private String phone_number;


}
