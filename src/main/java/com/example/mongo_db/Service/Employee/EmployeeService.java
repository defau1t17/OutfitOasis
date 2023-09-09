package com.example.mongo_db.Service.Employee;

import com.example.mongo_db.Entity.Employee.Employee;
import com.example.mongo_db.Entity.Employee.Employee_Account;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import com.example.mongo_db.Filter.FilterEmployees;
import com.example.mongo_db.Repository.EmployeesRepoes.EmployeeRepo;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
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
        employee_account.setUsername(generateUsername(employee.getPhone_number()));
        employee_account.setPassword(generateUserPassword());

        return employee_account;
    }

    public static String generateUsername(String employee_number) {
        String generatedUsername = employee_number;
        return generatedUsername;
    }

    public static String generateUserPassword() {
        PasswordGenerator generatedPassword = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "error";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = generatedPassword.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }


}
