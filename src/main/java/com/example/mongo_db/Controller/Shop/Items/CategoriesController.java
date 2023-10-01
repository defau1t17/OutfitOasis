package com.example.mongo_db.Controller.Shop.Items;


import com.example.mongo_db.Service.Items.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public String displayCategoriesPage(Model model) {
        model.addAttribute("categories", categoriesService.findAllCategories());
        return "/items/categories_page";
    }

}
