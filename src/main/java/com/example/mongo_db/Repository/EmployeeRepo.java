package com.example.mongo_db.Repository;

import com.example.mongo_db.Entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

    @Query("{'name' : ?0}")
    Employee findByNameTest(String empName);


    @Query(value = "{'name' :  ?0, 'experience': ?1}", fields = "{'rank' :  1}")
    Employee findByNameAndExpTest(String name, int exp);
}
