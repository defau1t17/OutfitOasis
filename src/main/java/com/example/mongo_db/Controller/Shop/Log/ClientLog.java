package com.example.mongo_db.Controller.Shop.Log;


import com.example.mongo_db.Entity.Client.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/client/")
public class ClientLog {


    @GetMapping("/registration")
    public String viewRegistrationPage(Model model) {
        model.addAttribute("newClient", new Client());
        return "shop/client/client_registration";
    }

//    @GetMapping("/authorization")
//    public String viewAuthPage(){
//
//    }

//    @GetMapping("/account/{id}")
//    public String displayClientAccountPage(){
//
//    }


}


