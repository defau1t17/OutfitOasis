package com.example.mongo_db.Controller.Shop.Items;


import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Service.Items.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shop/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public String displayAllItems(Model model) {
        model.addAttribute("all_items", catalogService.findAllItems());
        return "shop/items/catalog_page";
    }

    @GetMapping("/categories")
    public String displayCategoriesPage(Model model) {

        model.addAttribute("categories", catalogService.findAllCategories());
        return "shop/items/categories_page";
    }


    @GetMapping("/categories/{category}")
    public String displayItemsByCategory(@PathVariable(value = "category") String category, Model model) {
        model.addAttribute("items_by_category", catalogService.findAllItemsByCategory(category));
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
