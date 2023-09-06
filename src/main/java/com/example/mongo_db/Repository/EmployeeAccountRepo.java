package com.example.mongo_db.Repository;

import com.example.mongo_db.Entity.Employee_Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeAccountRepo extends MongoRepository<Employee_Account, String> {

    Employee_Account findByUsername(String username);
}
