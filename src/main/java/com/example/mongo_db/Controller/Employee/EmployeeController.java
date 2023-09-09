package com.example.mongo_db.Controller.Employee;


import com.example.mongo_db.Entity.Employee.Employee;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import com.example.mongo_db.Service.Queries.CheckForQuery;
import com.example.mongo_db.Service.Employee.EmployeeAccountService;
import com.example.mongo_db.Service.Employee.EmployeeService;
import com.example.mongo_db.Service.Queries.GenerateQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = Logger.getGlobal();

    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeAccountService employeeAccountService;

    private String query;


    @GetMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @Transactional
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            service.addEmployee(employee);
            employeeAccountService.saveEmployeeAccount(service.generateAccount(employee));
        }


        logger.log(Level.INFO, "new employee has been added successfully with params : " + employee);
        return "employee_add";

    }


    @GetMapping("/members")
    public String viewAllMembers(Model model) {
        model.addAttribute("members", service.findAllEmployees());

        logger.log(Level.INFO, "all members was displayed successfully");
        return "employee_members";
    }


    @GetMapping("/search")
    public String displayMembers(Model model) {
        model.addAttribute("members", service.findAllEmployees());
        model.addAttribute("filter", new EmployeeFilterDTO());

        logger.log(Level.INFO, "all members was displayed successfully");

        return "employees_search";
    }

    @PostMapping("/search")
    public String filterEmployees(@ModelAttribute @Valid EmployeeFilterDTO filterDTO, HttpServletRequest httpServletRequest) {
        List<Employee> employees = service.filterUsers(filterDTO);
        logger.info("list of filtered users created");


        query = new GenerateQuery().generateURL(filterDTO);


        if (employees.size() > 0) {

            httpServletRequest.getSession().setAttribute("query", query);

            logger.info("Found " + employees.size() + "user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;

        } else {

            httpServletRequest.getSession().setAttribute("query", query);

            logger.info("Not found user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;
        }

    }

    @GetMapping("/search/filtered/params")
    public String displayFilteredEmployees(HttpServletRequest httpServletRequest, Model model) {
        query = (String) httpServletRequest.getSession().getAttribute("query");
        model.addAttribute("notFound", "User not found!");

        if ((new CheckForQuery().doRedirectionNeeded(httpServletRequest))) {

            logger.info("redirected cause no params has been found");

            return "redirect:/employee/search";
        } else {
            EmployeeFilterDTO filterDTO = new CheckForQuery().findEmployeeFromQuery(httpServletRequest);

            if (filterDTO == null) {
                logger.info("caught null user and redirected to '/employee/search' page");
                return "redirect:/employee/search";
            } else {
                model.addAttribute("filter", filterDTO);
                List<Employee> filtered_employees = service.filterUsers(filterDTO);

                if (filtered_employees.size() > 0) {
                    model.addAttribute("filtered_employees", filtered_employees);

                    logger.info("object " + filterDTO + " successfully added to page, also " + filtered_employees.size() + " objects has been viewed");

                } else {
                    model.addAttribute("filtered_employees", null);
                    logger.info("object " + filterDTO + "not found  error message will be shown");

                }
            }
        }
        return "filtered_employees";
    }


    @PostMapping("search/filtered/params")
    public String displayFilteredEmployees(@ModelAttribute @Valid EmployeeFilterDTO filterDTO) {

        List<Employee> employees = service.filterUsers(filterDTO);

        query = new GenerateQuery().generateURL(filterDTO);

        if (employees.size() > 0) {

            logger.info("Found " + employees.size() + " object(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;

        } else if (employees.size() == 0) {

            logger.info("No object(s) found and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;


        }
        return "redirect:/employee/search";
    }
}