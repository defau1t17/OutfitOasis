package com.example.mongo_db.Controller.Shop.Client;


import com.example.mongo_db.Entity.Client.Address;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.CheckForAddress;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.LoginRedirection;
import com.example.mongo_db.Service.Clients.UpdateGlobalClient;
import com.example.mongo_db.Service.Image.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/shop/client/")
public class ClientController {
    private static final Logger logger = Logger.getGlobal();

    private static final String GLOBAL_CLIENT = "global_client";

    @Autowired
    private ClientsService service;

    @Autowired
    private ImageService imageService;

    @GetMapping("/registration")
    public String viewRegistrationPage(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute(GLOBAL_CLIENT) != null) {
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, null, request.getSession());
        }

        if (request.getSession().getAttribute("clientExists") == null) {
            model.addAttribute("newClient", new Client());
        } else {
            model.addAttribute("newClient", request.getSession().getAttribute("clientExists"));
            model.addAttribute("error_message", request.getSession().getAttribute("existed_params"));
        }

//        String referringPage = request.getHeader("referer");
//        if (referringPage != null) {
//            referringPage = referringPage.substring(referringPage.lastIndexOf('1') + 1, referringPage.length());
//            System.out.println(referringPage + " page");
//
//            if (referringPage.equals("/shop/client/login") || referringPage.equals("/shop/client/registration")) {
////                request.getSession().setAttribute("client_has_been_redirected_from_page", null);
//            } else {
//                request.getSession().setAttribute("client_has_been_redirected_from_page", referringPage);
//
//            }
//        }

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
            model.addAttribute("issue", "Your verification code doesn't matches! Try again");
        }
        Client newClient = (Client) request.getSession().getAttribute("newClient");

        model.addAttribute("info", newClient.getMail());

        return "shop/client/client_account_verification";
    }

    @Transactional
    @PostMapping("/registration/verification")
    public String verifyNewAccount(String user_verification_code, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String verification_code = (String) request.getSession().getAttribute("verification_code");

        if (verification_code.equals(user_verification_code)) {
            logger.info("user verified successfully. Redirected to new password page");
            service.saveNewClient((Client) request.getSession().getAttribute("newClient"));
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, (Client) request.getSession().getAttribute("newClient"), request.getSession());

            return "redirect:/shop/client/registration/address";
        } else {
            logger.info("User wrote incorrect verification code. Redirected back");
            redirectAttributes.addAttribute("issue", "WRONG_CODE");
            return "redirect:/shop/client/registration/verification";
        }
    }

    @GetMapping("/registration/address")
    public String displayAddressPage(Model model, HttpServletRequest request) {

        try {
            logger.info("all countries has been loaded to form");
            model.addAttribute("countries", service.getCountries());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        model.addAttribute("newAddress", new Address());

        logger.info("new address page has been loaded successfully");

        return "shop/client/client_address_page";
    }

    @PatchMapping("/registration/address")
    public String createNewAddress(@ModelAttribute("newAddress") Address address, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);

        if (CheckForAddress.isAddressNull(address)) {
            logger.info("client has skipped address page");
            client.setAddress(null);
            service.updateClient(client);

        } else {
            logger.info("added new address to client");
            client.setAddress(address);
            service.updateClient(client);
        }
        UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, client, request.getSession());

//        if (request.getSession().getAttribute("client_has_been_redirected_from_page") != null) {
//            return "redirect:" + request.getSession().getAttribute("client_has_been_redirected_from_page");
//        } else {
        return "redirect:/shop/client/account/" + client.getId();
    }
//    }


    @GetMapping("/login")
    public String displayClientLoginPage(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute(GLOBAL_CLIENT) != null) {
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, null, request.getSession());
        }
        logger.info("login page was shown successfully");

        String total_client = request.getParameter("client_user_name");
        String total_issue = request.getParameter("issue");

//        String referringPage = request.getHeader("referer");
//        if (referringPage != null) {
//            referringPage = referringPage.substring(referringPage.lastIndexOf('1') + 1, referringPage.length());
//            System.out.println(referringPage + " page");
//            if (referringPage.equals("/shop/client/login") || referringPage.equals("/shop/client/registration")) {
////                request.getSession().setAttribute("client_has_been_redirected_from_page", null);
//            } else {
//                request.getSession().setAttribute("client_has_been_redirected_from_page", referringPage);
//
//            }
//        }
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

        Client client = service.findClientByUserName(username);


        if (client != null) {
            if (client.getClient_password().equals(password)) {
                logger.info("Client was found successfully!");
                request.getSession().setAttribute("global_client", client);

//                if (request.getSession().getAttribute("client_has_been_redirected_from_page") != null) {
//                    return "redirect:" + request.getSession().getAttribute("client_has_been_redirected_from_page");
//                } else {
                return "redirect:/shop/client/account/" + client.getId();
//                }

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
            Optional<Client> clientById = service.findClientById(id);
            Client client = clientById.get();
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, client, request.getSession());
            logger.info("user verified successfully. Redirected to new password page");
            return "redirect:/shop/client/account/" + id + "/edit/password";
        } else {
            logger.info("User wrote incorrect verification code. Redirected back");
            redirectAttributes.addAttribute("issue", "WRONG_CODE");
            return "redirect:/shop/client/login/passwordrecovery/" + id + "/verification";
        }
    }

    @GetMapping("/account/{id}/edit/password")
    public String displayNewPasswordPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        String issue = request.getParameter("issue");
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);
        model.addAttribute("client_name", client.getClient_user_name());
        model.addAttribute("client_id", id);


        if (issue != null) {
            model.addAttribute("issue", "Password must not be the same!");
        }

        return "shop/client/client_new_password";
    }

    @Transactional
    @PatchMapping("/account/{id}/edit/password")
    public String changePassword(@PathVariable(value = "id") String id, @NotBlank @NotEmpty String new_password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);

        if (client.getClient_password().equals(new_password)) {
            return "redirect:/shop/client/account/" + id;
        } else {
            client.setClient_password(new_password);
            service.updateClient(client);
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, client, request.getSession());
            return "redirect:/shop/client/account/" + id;
        }
    }


    @GetMapping("/account/{id}")
    public String displayClientAccountPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {

        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);

        model.addAttribute("current_client", client);


        if (CheckForAddress.isAddressNull(client.getAddress())) {
            model.addAttribute("address", null);
        } else {
            model.addAttribute("address", client.getAddress());
        }

        logger.info("client account page was shown successfully!");

        return "shop/client/client_account_page";

    }

    @GetMapping("/account/{id}/edit")
    public String displayEditAccountPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {

        logger.info("edit client page was shown successfully");

        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);

        model.addAttribute("edit_client", client);
        try {
            model.addAttribute("countries", service.getCountries());
            logger.info("all countries was added to form successfully");
        } catch (Exception e) {
            System.out.println(e);
        }

        logger.info("client for edit was found and dispatched to form");

        if (request.getParameter("issue") != null) {
            model.addAttribute("issue", "SOMETHING WENT WRONG!");
        }
        return "shop/client/client_edit_account";

    }

    @Transactional
    @PatchMapping("/account/{id}/edit")
    public String editClient(@PathVariable(value = "id") String id, @ModelAttribute Client client, RedirectAttributes attributes, HttpServletRequest request) {
        Client updated_client = service.requestClientUpdate(client, CheckForAddress.isAddressNull(client.getAddress()));
        if (updated_client != null) {
            service.updateClient(updated_client);
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, updated_client, request.getSession());

            logger.info("all client's data was updated successfully");

            return "redirect:/shop/client/account/" + id;
        } else {
            attributes.addAttribute("issue", "FORBIDDEN");
            logger.info("something went wrong while  client's data");

            return "redirect/shop/client/account/" + id + "/edit";
        }

    }

    @GetMapping("/account/{id}/edit/mail")
    public String displayClientEditMailPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (request.getParameter("issue") != null) {
            model.addAttribute("issue", "This email is used by another Client!");
        }
        logger.info("client edit mail page was shown successfully!");
        return "shop/client/client_edit_mail_page";
    }


    @PostMapping("/account/{id}/edit/mail")
    public String requestClientEditMail(@PathVariable(value = "id") String id, @NotBlank @NotEmpty String newMail, HttpServletRequest request, RedirectAttributes attributes) {
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);
        if (!client.getMail().equals(newMail) && service.findClientByMail(newMail) == null) {
            String verification_code = service.sendVerificationMessage(newMail);
            request.getSession().setAttribute("verification_code_for_global_client", verification_code);
            request.getSession().setAttribute("mail_for_verification", newMail);

            logger.info("clients request has been send. Redirected to verification");
            return "redirect:/shop/client/account/" + id + "/edit/mail/verification";
        } else if (client.getMail().equals(newMail)) {
            logger.info("clients mail wasn't changed. Redirected to account page");

            return "redirect:/shop/client/account/" + id;
        } else {
            attributes.addAttribute("issue", "EMAIL_EXISTS");
            logger.info("mail that user send is client by another client! Redirected back");
            return "redirect:/shop/client/account/" + id + "/edit/mail";
        }
    }


    @GetMapping("/account/{id}/edit/mail/verification")
    public String displayClientEditMailVerificationPage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("mail_for_verification") == null) {
            return "redirect:/shop/client/account/" + id + "/edit/mail";
        } else {
            model.addAttribute("newMail", request.getSession().getAttribute("mail_for_verification"));
        }
        if (request.getParameter("issue") != null) {
            model.addAttribute("issue", "Your verification code doesn't matches! Try again");
        }

        logger.info("client edit mail verification page was shown successfully!");

        return "shop/client/client_account_verification";

    }

    @Transactional
    @PatchMapping("/account/{id}/edit/mail/verification")
    public String verifyClientEditMail(@PathVariable(value = "id") String id, String user_verification_code, HttpServletRequest request, RedirectAttributes attributes) {
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);
        String verification_code_for_global_client = (String) request.getSession().getAttribute("verification_code_for_global_client");
        String mail_for_verification = (String) request.getSession().getAttribute("mail_for_verification");


        if (verification_code_for_global_client.equals(user_verification_code)) {
            logger.info("client with id " + id + " changed mail from " + client.getMail() + " to " + mail_for_verification);
            client.setMail(mail_for_verification);
            service.updateClient(client);
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, client, request.getSession());
            return "redirect:/shop/client/account/" + id;
        } else {
            attributes.addAttribute("issue", "WRONG_CODE");
            logger.info("client send wrong verification code. Redirected back");
            return "redirect:/shop/client/account/" + id + "/edit/mail/verification";
        }
    }


    @GetMapping("/account/{id}/edit/image")
    public String displayClientEditImagePage(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        logger.info("upload image page has been shown successfully");

        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);

        model.addAttribute("global_client", client);

        return "shop/client/client_edit_image";
    }

    @Transactional
    @PostMapping("/account/{id}/edit/image")
    public String uploadImage(@PathVariable(value = "id") String id, @RequestParam(value = "image") MultipartFile image, RedirectAttributes attributes, HttpServletRequest request) throws IOException {
        Client client = (Client) request.getSession().getAttribute(GLOBAL_CLIENT);
        if (image != null) {
            imageService.uploadPhoto(image, client);
            UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, client, request.getSession());
            attributes.addAttribute("imageUpdated", true);
            return "redirect:/shop/client/account/" + id + "/edit/image";
        } else {
            attributes.addAttribute("issue", "SMTH_BAD");
            return "redirect:/shop/client/account/" + id + "/edit/image";
        }
    }


    @GetMapping("/account/{id}/logout")
    public String logOutPage(@PathVariable(value = "id") String id, HttpServletRequest request) {
        UpdateGlobalClient.updateGlobalClient(GLOBAL_CLIENT, null, request.getSession());
        return "redirect:/shop/client/login";
    }
}




