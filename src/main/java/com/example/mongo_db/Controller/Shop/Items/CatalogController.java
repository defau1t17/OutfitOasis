package com.example.mongo_db.Controller.Shop.Items;


import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Service.Items.CatalogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/shop/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping()
    public String displayAllItems(Model model, @RequestParam(value = "page", required = false) Optional<Integer> current_page) {
        model.addAttribute("all_items", catalogService.findAllItems());
        Page<ShopItem> page = catalogService.findByPage(current_page.orElse(0));


        model.addAttribute("page_information", page);

        model.addAttribute("page_count", IntStream.rangeClosed(0, page.getTotalPages() - 1).boxed().collect(Collectors.toList()));


        return "shop/items/catalog_page";
    }

    @GetMapping("/categories")
    public String displayCategoriesPage(Model model) {
        model.addAttribute("categories", catalogService.findAllCategories());
        return "shop/items/categories_page";
    }


    @GetMapping("/categories/{category}")
    public String displayItemsByCategory(@PathVariable(value = "category") String category, @RequestParam(value = "page") Optional<Integer> page, Model model) {
        Page<ShopItem> allItemsByCategory = catalogService.findAllItemsByCategory(category, page.orElse(0));
        model.addAttribute("category_page_information", allItemsByCategory);
        model.addAttribute("page_count", IntStream.rangeClosed(0, allItemsByCategory.getTotalPages() - 1).boxed().collect(Collectors.toList()));
        return "shop/items/catalog_page";
    }

    @GetMapping("/item/{id}")
    public String displayItemPage(@PathVariable(value = "id") String id, Model model) {
        ShopItem item = catalogService.findItemById(id);
        if (item != null) {
            model.addAttribute("item", item);
        }
        return "shop/items/item_page";
    }


}
