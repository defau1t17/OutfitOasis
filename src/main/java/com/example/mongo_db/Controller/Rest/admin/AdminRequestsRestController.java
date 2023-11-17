package com.example.mongo_db.Controller.Rest.admin;

import com.example.mongo_db.DTO.AdminRequestOperationDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Admin.AdminService;
import com.example.mongo_db.Service.Admin.RequestsOperations;
import com.example.mongo_db.Service.BugsAndQos.BugsAndQosService;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/api/admin")
public class AdminRequestsRestController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BugsAndQosService bugsAndQosService;

    @Autowired
    private RequestsOperations requestsOperations;

    @PostMapping("/requests")
    public ResponseEntity<?> adminRequestsProcessor(@RequestBody AdminRequestOperationDTO request, HttpServletRequest httpServletRequest) {
        if (adminService.operationValidation((Client) httpServletRequest.getSession().getAttribute("global_client"))) {
            if (requestsOperations.processOperation(request, adminService, bugsAndQosService, clientsService, mailSender, httpServletRequest)) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("transaction error");
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Operation rejected. Caused : " + ((Client) httpServletRequest.getSession().getAttribute("global_client")).getRole().toString());
    }


}
