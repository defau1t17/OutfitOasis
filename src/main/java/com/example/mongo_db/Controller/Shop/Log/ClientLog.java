package com.example.mongo_db.Controller.Shop.Log;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.LoginRedirection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;
import java.util.Optional;
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
        if (!service.doesClientExists(client)) {
            logger.info("new client was sent to verification successfully.");
            String verification_code = service.sendVerificationMessage(client.getMail());
            request.getSession().setAttribute("newClient", client);
            request.getSession().setAttribute("verification_code", verification_code);
            return "redirect:/shop/client/registration/verification";
        } else {
            request.getSession().setAttribute("clientExists", client);
            request.getSession().setAttribute("existed_params", service.existedFields(client));
            logger.info("client with same data was found! Redirected to registration page");
            return "redirect:/shop/client/registration";
        }
    }

    @GetMapping("/registration/verification")
    public String displayAccountVerificationPage(Model model, HttpServletRequest request) {
        logger.info("new client verification page was shown successfully!");
        String issue = request.getParameter("issue");
        if (issue != null) {
            model.addAttribute("issue", "Your verification code doesn't matches");
        }

        return "/shop/client/client_create_account_verification";
    }

    @PostMapping("/registration/verification")
    public String verifyNewAccount(String user_verification_code, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String verification_code = (String) request.getSession().getAttribute("verification_code");

        if (verification_code.equals(user_verification_code)) {
            logger.info("user verified successfully. Redirected to new password page");
            service.saveNewClient((Client) request.getSession().getAttribute("newClient"));
            return "redirect:/";
        } else {
            logger.info("User wrote incorrect verification code. Redirected back");
            redirectAttributes.addAttribute("issue", "WRONG_CODE");
            return "redirect:/shop/client/registration/verification";
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
        return "/shop/client/client_login";
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

    @GetMapping("/login/passwordrecovery")
    public String displayPasswordRecoveryPage(Model model, HttpServletRequest request) {
        logger.info("password recovery page was shown successfully");

        String client_mail = request.getParameter("client_mail");
        String issue = request.getParameter("issue");
        if (issue != null && client_mail != null) {
            model.addAttribute("incorrect_mail", client_mail);
            model.addAttribute("redirection_issue", "Client with such mail  not found");
        }

        return "shop/client/client_password_recovery";
    }

    @PostMapping("/login/passwordrecovery")
    public String recoverPassword(String client_mail, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Client clientByMail = service.findClientByMail(client_mail);
        if (clientByMail != null) {
            String recover_code = service.sendPasswordRecoveryMessage(client_mail);
            logger.info("recovery password has been send to " + client_mail + " successfully");

            request.getSession().setAttribute("recovery_code", recover_code);
            request.getSession().setAttribute("redirected_client", clientByMail);

            return "redirect:/shop/client/login/passwordrecovery/" + clientByMail.getId() + "/verification";
        } else {
            redirectAttributes.addAttribute("client_mail", client_mail);
            redirectAttributes.addAttribute("issue", "MAIL_NOT_FOUND");
            logger.info("clients mail not found! Redirected");
            return "redirect:/shop/client/login/passwordrecovery";
        }
    }

    @GetMapping("/login/passwordrecovery/{id}/verification")
    public String displayRecoveryVerificationPage(@PathVariable(value = "id", required = true) String id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String issue = request.getParameter("issue");
        model.addAttribute("client_verification_id", id);
        if (issue != null) {
            model.addAttribute("issue", "Your verification code doesn't matches");
        }
        Optional<Client> clientById = service.findClientById(id);
        if (clientById.isPresent()) {
            Client client = clientById.get();
            model.addAttribute("info", client.getMail());
        }
        logger.info("verification page for client with id " + id + " was shown successfully!");

        return "shop/client/client_password_recovery_verification";
    }

    @PostMapping("/login/passwordrecovery/{id}/verification")
    public String verifyPasswordRecovery(@PathVariable(value = "id", required = true) String id, String user_verification_code, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String recovery_code = (String) request.getSession().getAttribute("recovery_code");
        if (recovery_code.equals(user_verification_code)) {
            logger.info("user verified successfully. Redirected to new password page");
            return "redirect:/shop/client/login/update/password/" + id;

        } else {
            logger.info("User wrote incorrect verification code. Redirected back");
            redirectAttributes.addAttribute("issue", "WRONG_CODE");
            return "redirect:/shop/client/login/passwordrecovery/" + id + "/verification";
        }
    }

    @GetMapping("/login/update/password/{id}")
    public String displayNewPasswordPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        String issue = request.getParameter("issue");
        Optional<Client> clientById = service.findClientById(id);
        if (clientById.isPresent()) {
            Client client = clientById.get();
            model.addAttribute("client_name", client.getClient_user_name());
        } else {
            return "redirect:/shop/client/login/passwordrecovery";
        }

        if (issue != null) {
            model.addAttribute("issue", "Password must not be the same!");
        }


        return "/shop/client/client_new_password";
    }

    @Transactional
    @PatchMapping("/login/update/password/{id}")
    public String changePassword(@PathVariable(value = "id") String id, String new_password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Client redirected_client = (Client) request.getSession().getAttribute("redirected_client");
        if (redirected_client.getClient_password().equals(new_password)) {
            redirectAttributes.addAttribute("issue", "NOT_MODIFIED");
            return "redirect:/shop/client/login/update/password/" + id;
        } else {
            redirected_client.setClient_password(new_password);
            service.updateClientPassword(redirected_client);
            return "redirect:/";
        }
    }

//    @GetMapping("/account/{id}")
//    public String displayClientAccountPage(){
//
//    }


}


