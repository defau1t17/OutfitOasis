package com.example.mongo_db.Controller;

import com.example.mongo_db.Entity.Employee_Account;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Service.EmployeeAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/workers")
public class WorkersLogin {

    @Autowired
    private EmployeeAccountService service;

    @GetMapping("/login")
    public String displayLoginPage() {
        return "workersLogin";
    }


    @PostMapping("/login")
    public String checkForEmployee(String employeeUserName, String password, HttpServletRequest httpServletRequest) {

        System.out.println("got it");
        Optional<Employee_Account> employeeByUserName = service.findEmployeeByUserName(employeeUserName);
        if (employeeByUserName.isPresent()) {
            if (employeeByUserName.get().getPassword().equals(password) && employeeByUserName.get().getRole() == Role.ROLE_ADMIN) {
                httpServletRequest.getSession().setAttribute("currentEmployee", employeeByUserName.get().getEmployee());
                System.out.println("User found");
                return "redirect:/employee/add";
            } else {
                System.out.println("you dont have permission");
            }
        } else {
            System.out.println("user not found");
        }
        return "redirect:/";
    }


}
