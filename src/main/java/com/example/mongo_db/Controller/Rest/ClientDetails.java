package com.example.mongo_db.Controller.Rest;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/request/api")
public class ClientDetails {

    @GetMapping("/client/status")
    public HttpStatus isClientAuthorized(HttpServletRequest request) {
        if (request.getSession().getAttribute("global_client") != null) {

            return HttpStatus.FOUND;
        } else return HttpStatus.NOT_FOUND;
    }





}
