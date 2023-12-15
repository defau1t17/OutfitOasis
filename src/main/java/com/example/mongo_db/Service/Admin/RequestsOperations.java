package com.example.mongo_db.Service.Admin;

import com.example.mongo_db.DTO.AdminRequestOperationDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Entity.Requests.Types.RequestStatus;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Service.BugsAndQos.BugsAndQosService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.LogData.LoggerService;
import com.example.mongo_db.Service.MessageSenderService;
import com.example.mongo_db.Service.Producer.ProducerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestsOperations {

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private LoggerService loggerService;


    private void upgradeClientToProducerAndGenerateNewProducerAccount(Client client, GlobalRequests<RequestData> request, String clients_work_mail, ClientsService clientsService, JavaMailSender mailSender, String request_id, AdminService adminService) {
        producerService.generateProducerFromRequestAndSave(request.getData_inf(), client);
        client.setRole(Role.ROLE_PRODUCER);
        clientsService.update_entity(client);
        messageSenderService.sendClientNotificationAboutNewRole(clients_work_mail, mailSender);
    }

    private void denyClientsRequest(String request_id, AdminService adminService, String clients_work_mail, JavaMailSender mailSender) {
        GlobalRequests requests = adminService.getRequestByID(request_id).get();
        requests.setRequestStatus(RequestStatus.Denied);
        messageSenderService.sendClientNotificationAboutRejectionNewRole(clients_work_mail, mailSender);
    }

    private void removeRequests(String request_id, AdminService adminService, boolean approved) {
        if (approved) adminService.getRequestByID(request_id).get().setRequestStatus(RequestStatus.Approved);
        if (!approved) adminService.getRequestByID(request_id).get().setRequestStatus(RequestStatus.Denied);
        adminService.deleteRequestByID(request_id);
    }

    private String applyQosAndBugsRequest(String request_id, AdminService adminService, BugsAndQosService service) {
        GlobalRequests requests = adminService.getRequestByID(request_id).get();
        requests.setRequestStatus(RequestStatus.Approved);
        service.save_entity(requests);
        return service.findNewEntityByOldID(request_id);
    }

    public boolean processOperation(AdminRequestOperationDTO data, AdminService adminService, BugsAndQosService bugsAndQosService, ClientsService clientsService, JavaMailSender mailSender, HttpServletRequest httpServletRequest) {
        Optional<GlobalRequests> getById = adminService.getRequestByID(data.getRequest_id());
        Client admin = (Client) httpServletRequest.getSession().getAttribute("global_client");
        if (getById.isPresent()) {
            GlobalRequests request = getById.get();
            switch (data.getOperation()) {
                case "APPLY":
                    if (request.getTag() == RequestTags.BUG || request.getTag() == RequestTags.CLIENT_QOS) {
                        String newRequestID = applyQosAndBugsRequest(request.getId(), adminService, bugsAndQosService);
                        loggerService.log(admin.getId(), "applied request with ID [" + request.getId() + "] ->  new ID [" + newRequestID + "]");
                        removeRequests(request.getId(), adminService, true);
                        return true;
                    } else if (request.getTag() == RequestTags.PRODUCER_NEW) {
                        upgradeClientToProducerAndGenerateNewProducerAccount(request.getRequest_sender(), request, request.getRequest_sender().getMail(), clientsService, mailSender, request.getId(), adminService);
                        loggerService.log(admin.getId(), "approved role reversal for " + request.getRequest_sender().getId() + " client");
                        loggerService.log(request.getRequest_sender().getId(), "role reversed to PRODUCER");
                        removeRequests(request.getId(), adminService, true);
                        return true;
                    } else return false;
                case "DENY":
                    denyClientsRequest(request.getId(), adminService, request.getRequest_sender().getMail(), mailSender);
                    loggerService.log(admin.getId(), "denied role reversal for " + request.getRequest_sender().getId() + " client | request " + request.displayRequest());
                    loggerService.log(request.getRequest_sender().getId(), "role reversal request denied");
                    removeRequests(request.getId(), adminService, false);
                    return true;
                case "REMOVE":
                    loggerService.log(admin.getId(), "removed request " + request.displayRequest());
                    removeRequests(request.getId(), adminService, false);
                    return true;
            }
        } else return false;
        return false;
    }


}
