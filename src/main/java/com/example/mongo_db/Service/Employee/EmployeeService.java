package com.example.mongo_db.Service.Employee;

import com.example.mongo_db.Entity.Employee.Employee;
import com.example.mongo_db.Entity.Employee.Employee_Account;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import com.example.mongo_db.Filter.FilterEmployees;
import com.example.mongo_db.Repository.EmployeesRepoes.EmployeeRepo;
import com.example.mongo_db.Service.GenerateSpecialCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private EmployeeRepo repo;

    @Autowired
    private GenerateSpecialCode generateSpecialCode;


    public void addEmployee(Employee employee) {
        repo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return repo.findAll();
    }


    public List<Employee> filterUsers(EmployeeFilterDTO filterDTO) {
        List<Employee> filteredEmployees = (List<Employee>) mongoTemplate.find(new FilterEmployees().filter(filterDTO), Employee.class);
        return filteredEmployees;
    }

    public Employee_Account generateAccount(Employee employee) {
        Employee_Account employee_account = new Employee_Account();
        employee_account.setEmployee(employee);
        employee_account.setUsername(employee.getPhone_number());
        employee_account.setPassword(generateSpecialCode.createCode());

        return employee_account;
    }


}
