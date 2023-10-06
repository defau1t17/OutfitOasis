package com.example.mongo_db.Controller.Shop.Producer;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/shop/producer")
public class ProducerController {

    @Autowired
    private ClientsService service;

    @Autowired
    private RequestsService requestsService;

    private static final Logger logger = Logger.getGlobal();


    @GetMapping("/request/form")
    public String displayProducerForm(Model model, HttpServletRequest request) {


        try {
            model.addAttribute(

                    "countries", service.getCountries());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (requestsService.isClientOnModeration((Client) request.getSession().getAttribute("global_client"))) {
            model.addAttribute("onModeration", true);
            logger.info("client found in moderation list. All fields hidden");
        } else {
            model.addAttribute("onModeration", false);
            logger.info("client not found in moderation list.");
        }

        logger.info("Producer form page was shown successfully");


        return "shop/producer/producer_request_form_page";
    }
}
