package com.example.mongo_db.Controller.Shop.Log;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.LoginRedirection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping("/login")
    public String displayClientLoginPage(Model model, HttpServletRequest request) {

        logger.info("login page was shown successfully");

        String total_client = request.getParameter("client_user_name");
        String total_issue = request.getParameter("issue");


        if (total_client != null) {
            if (LoginRedirection.addModels(total_client)) {
                model.addAttribute("client_user_name", total_client);
                model.addAttribute("issue", LoginRedirection.printIssue(total_issue));
                logger.info("model was added successfully");
            }
        }


        return "shop/client/client_login";
    }

    @PostMapping("/login")
    public String loginClient(String username, String password, HttpServletRequest request, RedirectAttributes attributes) {
//        request.getSession().setAttribute("login_message", "waiting for attributes");

        Client client = service.findClientByUserName(username);

        if (client != null) {
            if (client.getClient_password().equals(password)) {
                logger.info("Client was found successfully! ");
                return "redirect:/";
            } else {
                logger.info("Client wrote wrong password");
                attributes.addAttribute("client_user_name", username);
                attributes.addAttribute("issue", "WRONG_PASSWORD");

                return "redirect:/shop/client/login";
            }

        } else {
            logger.info("client not found");
            attributes.addAttribute("client_user_name", username);
            attributes.addAttribute("issue", "USER_NOT_FOUND");
            return "redirect:/shop/client/login";
        }

    }

    @GetMapping("/passwordrecovery")
    public String displayPasswordRecoveryPage(Model model) {
        logger.info("password recovery page was shown successfully");
        return "/shop/client/client_password_recovery";
    }

    @PostMapping("/passwordrecovery")
    public String recoverPassword(String client_mail, RedirectAttributes redirectAttributes){


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


