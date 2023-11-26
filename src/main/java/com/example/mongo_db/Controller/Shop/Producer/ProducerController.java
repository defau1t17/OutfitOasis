package com.example.mongo_db.Controller.Shop.Producer;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/shop/producer")
public class ProducerController {

    @Autowired
    private ClientsService service;

    @Autowired
    private RequestsService requestsService;

    @GetMapping("/request/form")
    public String displayProducerForm(Model model, HttpServletRequest request) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        try {
            model.addAttribute("countries", service.getCountries());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Client client = (Client) request.getSession().getAttribute("global_client");

        model.addAttribute("status", requestsService.getProducerStatus(client));
        return "shop/producer/producer_request_form_page";
    }


    @GetMapping("/account/{id}")
    public String displayProducerPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {


        return "shop/producer/producer_page";
    }
}
