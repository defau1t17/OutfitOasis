package com.example.mongo_db.Service;

import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Post;
import com.example.mongo_db.Entity.Rank;
import com.example.mongo_db.Filter.EmployeeFilterDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CheckForQuery {

    public boolean doRedirectionNeeded(HttpServletRequest request) {
        boolean doRedirectionNeeded = false;
        Enumeration<String> parameterNames = request.getParameterNames();
        if (!parameterNames.asIterator().hasNext()) {
            doRedirectionNeeded = true;
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
                    employeeFilterDTO.setAge(Integer.parseInt(value));
                    break;
                case "gender":
                    if (Gender.values().equals(value)) {
                        employeeFilterDTO.setGender(Gender.valueOf(value));
                    } else {
                        employeeFilterDTO.setGender(null);
                    }
                    break;
                case "experience":
                    employeeFilterDTO.setExperience(Integer.parseInt(value));

                    break;
                case "rank":
                    if (Rank.values().equals(value)) {
                        employeeFilterDTO.setRank(Rank.valueOf(value));
                    } else {
                        employeeFilterDTO.setRank(null);
                    }
                    break;
                case "post":
                    if (Post.values().equals(value)) {
                        employeeFilterDTO.setPost(Post.valueOf(value));
                    } else {
                        employeeFilterDTO.setPost(null);
                    }
                    break;

                default:
                    return null;
            }

        }
        if (employeeFilterDTO.getName() == null) employeeFilterDTO.setName("");
        if (employeeFilterDTO.getSecond_name() == null) employeeFilterDTO.setSecond_name("");


        return employeeFilterDTO;
    }
}

