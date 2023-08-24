package com.example.mongo_db.Filter;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Post;
import com.example.mongo_db.Entity.Rank;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class EmployeeFilterDTO {


    private String name;
    private String second_name;
    private int age;
    private Gender gender;
    private int experience;
    private Post post;
    private Rank rank;
}
