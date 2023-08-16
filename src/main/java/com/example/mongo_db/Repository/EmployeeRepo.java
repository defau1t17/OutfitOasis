package com.example.mongo_db.Repository;

import com.example.mongo_db.Entity.Employee;
import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Rank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, UUID> {

    List<Employee> findAllByRank(Rank rank);

    List<Employee> findAllByExperience();


    List<Employee> findAllByPost(String post);

    List<Employee> findAllByName(String name);

    Employee findByName(String name);

    Employee findBySecond_name(String second_name);

    List<Employee> findAllByAge(int age);

    List<Employee> findAllByGender(Gender gender);

}
