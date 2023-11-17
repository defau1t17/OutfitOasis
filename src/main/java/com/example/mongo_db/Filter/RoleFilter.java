package com.example.mongo_db.Filter;

import com.example.mongo_db.Entity.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class RoleFilter {

    public Query filterUsersByRole(List<String> roles) {
        Query query = new Query();
        if (roles != null && !roles.isEmpty()) {
            query.addCriteria(Criteria.where("role").in(roles));
        }
        return query;
    }

}
