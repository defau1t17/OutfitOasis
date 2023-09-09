package com.example.mongo_db.Repository.EmployeesRepoes;

import com.example.mongo_db.Entity.Employee.Employee_Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAccountRepo extends MongoRepository<Employee_Account, String> {

    Employee_Account findByUsername(String username);
}
