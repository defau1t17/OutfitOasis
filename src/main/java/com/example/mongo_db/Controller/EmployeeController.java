package com.example.mongo_db.Controller;


import com.example.mongo_db.Entity.*;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import com.example.mongo_db.Service.EmployeeService;
import com.example.mongo_db.Service.GenerateQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpResponse;
import java.util.List;
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
    public String filterEmployees(@ModelAttribute EmployeeFilterDTO filterDTO, Model model, HttpServletRequest httpServletRequest) {
        List<Employee> employees = service.filterUsers(filterDTO);

        if (employees.size() > 0) {
            query = new GenerateQuery().generateURL(filterDTO);

//            if(query.startsWith("?") && query.endsWith("?") && employees.size()>0){
//
//                return "redirect:/employee/search";
//            }

            httpServletRequest.getSession().setAttribute("filtered_employees", employees);
            httpServletRequest.getSession().setAttribute("params", filterDTO);
            httpServletRequest.getSession().setAttribute("query", query);

            logger.info("Found " + employees.size() + "user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;

        } else {

            query = new GenerateQuery().generateURL(filterDTO);

//            if (query.startsWith("?") && query.endsWith("?")) {
//                return "redirect:/employee/search";
//            }

            httpServletRequest.getSession().setAttribute("filtered_employees", null);
            httpServletRequest.getSession().setAttribute("params", filterDTO);

            logger.info("Not found user(s) and redirected to page with params : " + query);
            return "redirect:/employee/search/filtered/params" + query;
        }

    }


    @GetMapping("/search/filtered/params")
    public String displayFilteredEmployees(HttpServletRequest httpServletRequest, Model model) {
        List<Employee> filtered_employees = (List<Employee>) httpServletRequest.getSession().getAttribute("filtered_employees");
        EmployeeFilterDTO filterDTO = (EmployeeFilterDTO) httpServletRequest.getSession().getAttribute("params");
        query = (String) httpServletRequest.getSession().getAttribute("query");

        System.out.println("query from filtered get : " + query);

//        if (query == null) {
//            return "redirect:/employee/search";
//        }
//        if (query.startsWith("?") && query.endsWith("&")) {
//            return "redirect:/employee/search";
//        }


        model.addAttribute("filter", filterDTO);
        model.addAttribute("filtered_employees", filtered_employees);
        model.addAttribute("notFound", "User not found!");


        return "filtered_employees";
    }


    @PostMapping("search/filtered/params")
    public String displayFilteredEmployees(HttpServletRequest httpServletRequest, Model model, @ModelAttribute EmployeeFilterDTO filterDTO) {
        List<Employee> employees = service.filterUsers(filterDTO);

        if (employees.size() > 0) {

            query = new GenerateQuery().generateURL(filterDTO);
            httpServletRequest.getSession().setAttribute("filtered_employees", employees);
            httpServletRequest.getSession().setAttribute("params", filterDTO);

            logger.info("Found " + employees.size() + " user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params" + query;

        } else if (employees.size() == 0) {


            query = new GenerateQuery().generateURL(filterDTO);

//            if (query.startsWith("?") && query.endsWith("?")) {
//                return "redirect:/employee/search";
//            }

            httpServletRequest.getSession().setAttribute("filtered_employees", null);
            httpServletRequest.getSession().setAttribute("params", filterDTO);
            logger.info("Not found user(s) and redirected to page with params : " + query);

            return "redirect:/employee/search/filtered/params?" + query;


        }
        return "filtered_employees";
    }
}