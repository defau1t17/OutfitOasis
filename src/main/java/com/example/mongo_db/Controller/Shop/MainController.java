package com.example.mongo_db.Controller.Shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {

    @GetMapping
    public String displayMainPage() {
        return "redirect:/shop/catalog";
    }


}
