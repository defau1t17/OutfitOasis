package com.example.mongo_db.Filter;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public class RequestsFilter {
    public Query filterRequestsByTags(List<String> tags) {
        Query query = new Query();
        if (tags != null && !tags.isEmpty()) {
            query.addCriteria(Criteria.where("tag").in(tags));
        }
        return query;
    }
}
