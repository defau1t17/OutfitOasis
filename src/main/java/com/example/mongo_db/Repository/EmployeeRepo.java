package com.example.mongo_db.Repository;

import com.example.mongo_db.Entity.Employee;
import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Rank;
import com.example.mongo_db.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, UUID> {

    List<Employee> findAllByRank(Rank rank);

    List<Employee> findAllByExperience();


    List<Employee> findAllByPost(String post);

    @Query("{'name' : ?0}")
    Employee findByNameTest(String empName);


    @Query(value = "{'name' :  ?0, 'experience': ?1}", fields = "{'rank' :  1}")
    Employee findByNameAndExpTest(String name, int exp);
}
