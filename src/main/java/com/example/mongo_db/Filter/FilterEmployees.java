package com.example.mongo_db.Filter;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class FilterEmployees {

    public Query filter(EmployeeFilterDTO filterDTO) {
        Query query = new Query();

        System.out.println(filterDTO.toString());
        if (!filterDTO.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").is(filterDTO.getName()));
        }
        if (!filterDTO.getSecond_name().isEmpty()) {
            query.addCriteria(Criteria.where("second_name").is(filterDTO.getSecond_name()));
        }
        if (filterDTO.getAge() > 0) {
            query.addCriteria(Criteria.where("age").gte(filterDTO.getAge()));
        }
        if (filterDTO.getGender() != null) {
            query.addCriteria(Criteria.where("gender").is(filterDTO.getGender()));
        }
        if (filterDTO.getExperience() > 0) {
            query.addCriteria(Criteria.where("experience").gte(filterDTO.getExperience()));
        }
        if (filterDTO.getRank() != null) {
            query.addCriteria(Criteria.where("rank").is(filterDTO.getRank()));
        }
        if (filterDTO.getPost() != null) {
            query.addCriteria(Criteria.where("post").is(filterDTO.getPost()));
        }
        return query;

    }


}
