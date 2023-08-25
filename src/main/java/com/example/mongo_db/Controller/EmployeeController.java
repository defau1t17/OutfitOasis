package com.example.mongo_db.Controller;


import com.example.mongo_db.Entity.*;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import com.example.mongo_db.Service.CheckForQuery;
import com.example.mongo_db.Service.EmployeeService;
import com.example.mongo_db.Service.GenerateQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = Logger.getGlobal();

    @Autowired
    private EmployeeService service;

    private String query;


    @GetMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @Transactional
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        service.addEmployee(employee);
        logger.info("new employee has been added successfully");
        return "employee_add";

    }


    @GetMapping("/members")
    public String viewAllMembers(Model model) {
        model.addAttribute("members", service.findAllEmployees());
        return "employee_members";
    }


    @GetMapping("/search")
    public String displayMembers(Model model) {
        model.addAttribute("members", service.findAllEmployees());
        model.addAttribute("filter", new EmployeeFilterDTO());
        return "employees_search";
    }

    @PostMapping("/search")
    public String filterEmployees(@ModelAttribute @Valid EmployeeFilterDTO filterDTO, Model model, HttpServletRequest httpServletRequest) {
        List<Employee> employees = service.filterUsers(filterDTO);
        query = new GenerateQuery().generateURL(filterDTO);

        if (employees.size() > 0) {

            httpServletRequest.getSession().setAttribute("filtered_employees", employees);
            httpServletRequest.getSession().setAttribute("params", filterDTO);
            httpServletRequest.getSession().setAttribute("query", query);

            logger.info("Found " + employees.size() + "user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;

        } else {

            httpServletRequest.getSession().setAttribute("filtered_employees", null);
            httpServletRequest.getSession().setAttribute("params", filterDTO);
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

            Map<String, String> wrongParams = (Map<String, String>) httpServletRequest.getSession().getAttribute("wrongParams");
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
    public String displayFilteredEmployees(@ModelAttribute @Valid EmployeeFilterDTO filterDTO ) {

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