package com.example.mongo_db.Service.Queries;

import com.example.mongo_db.Filter.EmployeeFilterDTO;

public class GenerateQuery {

    public String generateURL(EmployeeFilterDTO filterDTO) {
        String query = "?";
        if (!filterDTO.getName().isEmpty() && !filterDTO.getName().isBlank()) {
            query += "name=" + filterDTO.getName() + "&";
        }
        if (!filterDTO.getSecond_name().isEmpty() && !filterDTO.getSecond_name().isBlank()) {
            query += "second_name=" + filterDTO.getSecond_name() + "&";
        }
        if (filterDTO.getAge() > 0) {
            query += "age=" + filterDTO.getAge() + "&";
        }
        if (filterDTO.getGender() != null) {
            query += "gender=" + filterDTO.getGender() + "&";
        }
        if (filterDTO.getExperience() > 0) {
            query += "exp=" + filterDTO.getExperience() + "&";
        }
        if (filterDTO.getRank() != null) {
            query += "rank=" + filterDTO.getRank() + "&";
        }
        if (filterDTO.getPost() != null) {
            query += "post=" + filterDTO.getPost() + "&";
        }

        if (query.endsWith("&")) {
            query = query.substring(0, query.length() - 1);
        }

        return query;
    }
}
