package com.example.mongo_db.Service;

import com.example.mongo_db.Entity.Employee;
import com.example.mongo_db.Entity.Rank;
import com.example.mongo_db.Repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo repo;

    private List<Employee> employees;


    public void addEmployee(Employee employee) {
        repo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return repo.findAll();
    }

    public List<Employee> findAllByRank(Rank rank) {
        employees = repo.findAllByRank(rank);
        return employees;
    }

    public List<Employee> findAllByPost(String post) {
        employees = repo.findAllByPost(post);
        return employees;
    }

    public List<Employee> findAllByExp() {
        employees = repo.findAllByExperience();
        return employees;
    }

    public Employee findByNameQuery(String name) {
        return repo.findByNameTest(name);
    }

    public Employee findByNameAndExpQuery(String name, int exp) {
        return repo.findByNameAndExpTest(name, exp);
    }


}
