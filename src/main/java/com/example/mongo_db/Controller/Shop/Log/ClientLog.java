package com.example.mongo_db.Controller.Shop.Log;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/shop/client/")
public class ClientLog {


    private Logger logger = Logger.getGlobal();


    @Autowired
    private ClientsService service;

    @GetMapping("/registration")
    public String viewRegistrationPage(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute("clientExists") == null) {
            model.addAttribute("newClient", new Client());
        } else {
            model.addAttribute("newClient", request.getSession().getAttribute("clientExists"));
            model.addAttribute("error_message", request.getSession().getAttribute("existed_params"));
        }
        logger.info("create new client page was shown successfully!");
        return "shop/client/client_registration";
    }


    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute Client client, HttpServletRequest request) {
        ResponseEntity<Client> clientResponseEntity = service.saveNewClient(client);
        if (clientResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            logger.info("new client was created successfully!");
            return "redirect:/employee/members";
        } else if (clientResponseEntity.getStatusCode() == HttpStatus.FORBIDDEN) {
            request.getSession().setAttribute("clientExists", client);
            request.getSession().setAttribute("existed_params", service.existedFields(client));
            logger.info("client with same data was found! Redirected to registration page");
            return "redirect:/shop/client/registration";
        } else {
            return "redirect:/";
        }
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


