package com.example.mongo_db.Service.Items;


import com.example.mongo_db.Entity.Items.models.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoriesService {

    public List<Category> findAllCategories() {
        return Arrays.asList(Category.values());
    }
}
