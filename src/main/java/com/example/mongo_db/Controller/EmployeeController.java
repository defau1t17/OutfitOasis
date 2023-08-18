package com.example.mongo_db.Controller;


import com.example.mongo_db.Entity.*;
import com.example.mongo_db.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = Logger.getGlobal();

    @Autowired
    private EmployeeService service;


    @GetMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @Transactional
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employee.setId(UUID.randomUUID());
        service.addEmployee(employee);
        logger.info("new employee has been added successfully");
        logger.info(service.findByNameQuery("test").toString());
        logger.info(service.findByNameAndExpQuery("test", 3).toString());
        return "employee_add";

    }


    @GetMapping("/members")
    public String viewAllMembers(Model model) {
        model.addAttribute("members", service.findAllEmployees());
        return "employee_members";
    }

    @GetMapping("/search")
    public String displaySearchPage(Model model) {
        model.addAttribute("members", service.findAllEmployees());
        return "employee_search";
    }





}
