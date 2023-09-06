package com.example.mongo_db.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "employees_acc")
public class Employee_Account {

    private Employee employee;

    @Id
    private String accountId;
    private String username;
    private String password;
    private Role role = Role.ROLE_USER;
}
