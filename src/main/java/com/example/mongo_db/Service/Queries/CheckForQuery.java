package com.example.mongo_db.Service.Queries;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Employee.Post;
import com.example.mongo_db.Entity.Rank;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.error.ShouldBeAlphabetic;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckForQuery {

    private Logger logger = Logger.getGlobal();

    public boolean doRedirectionNeeded(HttpServletRequest request) {
        boolean doRedirectionNeeded = false;
        Enumeration<String> parameterNames = request.getParameterNames();
        if (!parameterNames.asIterator().hasNext()) {
            doRedirectionNeeded = true;
            logger.log(Level.INFO, "no params found. Redirection required");
        }

        return doRedirectionNeeded;
    }

    public EmployeeFilterDTO findEmployeeFromQuery(HttpServletRequest httpServletRequest) {
        EmployeeFilterDTO employeeFilterDTO = new EmployeeFilterDTO();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        if (!parameterNames.asIterator().hasNext()) {
            return null;
        }


        while (parameterNames.asIterator().hasNext()) {

            String param = parameterNames.asIterator().next();
            String value = httpServletRequest.getParameter("" + param);


            switch (param) {
                case "name":
                    employeeFilterDTO.setName(value);
                    break;
                case "second_name":
                    employeeFilterDTO.setSecond_name(value);
                    break;
                case "age":
                    if (Integer.parseInt(value) >= 0) {
                        employeeFilterDTO.setAge(Integer.parseInt(value));
                        System.out.println(ShouldBeAlphabetic.shouldBeAlphabetic(Integer.parseInt(value)));
                    } else {
                        employeeFilterDTO.setAge(0);
                    }
                    break;
                case "gender":
                    if (Gender.valueOf(value) != null) {
                        employeeFilterDTO.setGender(Gender.valueOf(value));
                    } else {
                        employeeFilterDTO.setGender(null);
                    }
                    break;
                case "exp":
                    if (Integer.parseInt(value) >= 0) {
                        employeeFilterDTO.setExperience(Integer.parseInt(value));
                    } else {
                        employeeFilterDTO.setExperience(0);
                    }
                    break;
                case "rank":
                    if (Rank.valueOf(value) != null) {
                        employeeFilterDTO.setRank(Rank.valueOf(value));
                    } else {
                        employeeFilterDTO.setRank(null);
                    }
                    break;
                case "post":
                    if (Post.valueOf(value) != null) {
                        employeeFilterDTO.setPost(Post.valueOf(value));
                    } else {
                        employeeFilterDTO.setPost(null);
                    }
                    break;

                default:
                    logger.log(Level.WARNING, "got some issue with params and return null model");
                    return null;
            }

        }
        if (employeeFilterDTO.getName() == null) employeeFilterDTO.setName("");
        if (employeeFilterDTO.getSecond_name() == null) employeeFilterDTO.setSecond_name("");

        logger.info("generated Filtered Model - " + employeeFilterDTO + " and send to page");

        return employeeFilterDTO;
    }
}

