package com.example.mongo_db.Filter;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalClientsFilter {

    public Query filterUsersByParams(List<String> roles, String name, String secondName, int age, String gender) {
        Query query = new Query();
        if (roles != null && !roles.isEmpty()) {
            query.addCriteria(Criteria.where("role").in(roles));
        }
        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i"));
        }
        if (secondName != null && !secondName.isEmpty()) {
            query.addCriteria(Criteria.where("second_name").regex(secondName, "i"));
        }
        if (age > 0 && age < 110) {
            query.addCriteria(Criteria.where("age").gt(age));
        }
        if (gender != null && !gender.isEmpty()) {
            query.addCriteria(Criteria.where("gender").is(gender));
        }
        return query;
    }

}
