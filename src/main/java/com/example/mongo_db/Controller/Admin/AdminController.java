package com.example.mongo_db.Controller.Admin;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/administration/admin/")
public class AdminController {

    @GetMapping("/panel/{id}")
    public String displayAdminPanelPage(@PathVariable(value = "id")String id, Model model, HttpServletRequest request){

        return "/shop/admin/main_admin_panel_page";
    }


}
