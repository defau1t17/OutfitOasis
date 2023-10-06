package com.example.mongo_db.Filter;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Employee.Post;
import com.example.mongo_db.Entity.Employee.Rank;
import lombok.Data;

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
