package com.example.mongo_db.Service;


import com.example.mongo_db.Entity.Employee_Account;
import com.example.mongo_db.Repository.EmployeeAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeAccountService {

    @Autowired
    private EmployeeAccountRepo employeeAccountRepo;

    public void saveEmployeeAccount(Employee_Account account) {
        employeeAccountRepo.save(account);
    }

    public Optional<Employee_Account> findEmployeeByUserName(String username) {
        if (employeeAccountRepo.findByUsername(username) != null) {
            return Optional.of(employeeAccountRepo.findByUsername(username));
        } else return Optional.empty();
    }


}
