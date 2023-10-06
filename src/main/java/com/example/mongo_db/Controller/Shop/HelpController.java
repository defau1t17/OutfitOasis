package com.example.mongo_db.Controller.Shop;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/help")
public class HelpController {


    @GetMapping
    public String displayHelpPage() {
        return "shop/help_page";
    }

}
