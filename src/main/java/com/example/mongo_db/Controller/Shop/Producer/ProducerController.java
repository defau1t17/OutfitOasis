package com.example.mongo_db.Controller.Shop.Producer;


import com.example.mongo_db.DTO.NewProducerItemDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Producer.ProducerService;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.util.BsonUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/shop/producer")
public class ProducerController {

    @Autowired
    private ClientsService service;

    @Autowired
    private ProducerService producerService;

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


    @GetMapping("/{id}")
    public String displayProducerPageForClients(@PathVariable(value = "id") String id, Model model,
                                                @RequestParam(required = false, name = "page") Optional<Integer> page,
                                                HttpServletRequest request) {
        Optional<Producer> optionalProducer = producerService.findProducerByID(id);
        if (optionalProducer.isPresent()) {
            model.addAttribute("producer", optionalProducer.get());
            model.addAttribute("producerItems", producerService.getPageProducerItems(page.orElse(0), optionalProducer.get().getId()));
        } else {
            //throw not found window
        }
        return "shop/producer/producer_page";
    }

    @GetMapping("/{id}/creator")
    public String displayProducerAccountPage(@PathVariable(value = "id") String id, Model model) {
        Optional<Producer> optionalProducer = producerService.findProducerByID(id);
        if (optionalProducer.isPresent()) {
            model.addAttribute("producer", optionalProducer.get());
        } else {
            //throw not found window
        }
        return "shop/producer/producer_personal_account_page";
    }

    @GetMapping("/{id}/creator/products")
    public String displayProducerAllProducts(@PathVariable(value = "id") String id,
                                             @RequestParam(name = "page", required = false) Optional<Integer> page,
                                             Model model) {
        Optional<Producer> optionalProducer = producerService.findProducerByID(id);
        if (optionalProducer.isPresent()) {
            model.addAttribute("products", producerService.getPageProducerItems(page.orElse(0), id));
        } else {
            //throw not found window
        }

        return "shop/producer/manage_products";
    }

    @GetMapping("/{id}/creator/product")
    public String createNewProduct(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("producerID", id);
        model.addAttribute("newProducerItemDTO", new NewProducerItemDTO());
        return "shop/producer/new_product";
    }


    @GetMapping("/{id}/creator/team")
    public String editProducersTeam(@PathVariable(value = "id") String id, Model model) {


        return "shop/producer/manage_products";
    }


}
