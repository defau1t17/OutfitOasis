package com.example.mongo_db.Controller.Shop.Producer;


import com.example.mongo_db.Service.Clients.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/shop/producer")
public class ProducerController {

    @Autowired
    private ClientsService service;


    @GetMapping("/request/form")
    public String displayProducerForm(Model model) {
        try {
            model.addAttribute("countries", service.getCountries());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "shop/producer/producer_request_form_page";
    }
}
