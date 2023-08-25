package com.example.mongo_db.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee extends User {

    @NotBlank
    @NotEmpty
    @NotNull
    private int experience;
    @NotBlank
    @NotEmpty
    private Rank rank;
    @NotBlank
    @NotEmpty
    private Post post;

}
