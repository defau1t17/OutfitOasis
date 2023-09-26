package com.example.mongo_db.Entity.Parse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Country {
    private String name;
    @JsonIgnore
    private String code;
}
