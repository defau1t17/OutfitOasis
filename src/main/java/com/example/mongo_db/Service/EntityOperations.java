package com.example.mongo_db.Service;

public interface EntityOperations {
    void save_entity(Object obj);

    void update_entity(Object obj);

    void remove_entity(Object obj);

}
