package com.example.mongo_db.Service.Admin;

import com.example.mongo_db.DTO.SendRequestOperationDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.Types.RequestStatus;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Service.BugsAndQos.BugsAndQosService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.SendMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

public class RequestOperations {

    private static final String OUTFIT_OASIS_SENDER = "onlineshop.project@yandex.com";

    private void upgradeClientsRoleToProducer(Client client, String clients_work_mail, ClientsService clientsService, JavaMailSender mailSender, String request_id, AdminService adminService) {
        client.setRole(Role.ROLE_PRODUCER);
        clientsService.update_entity(client);
        SendMessage.sendMessageForNewClientsRole(clients_work_mail, OUTFIT_OASIS_SENDER, mailSender);
        adminService.deleteRequestByID(request_id);

    }

    private void denyClientsRequest(String request_id, AdminService service, String clients_work_mail, JavaMailSender mailSender) {
        service.deleteRequestByID(request_id);
        SendMessage.sendDenyForNewRoleToClient(clients_work_mail, OUTFIT_OASIS_SENDER, mailSender);
    }

    private void logAndRemoveApprovedRequestOrRoleUpdateRequest(String request_id, AdminService adminService) {
        Optional<GlobalRequests> requestByID = adminService.getRequestByID(request_id);
        requestByID.ifPresent(globalRequests -> globalRequests.setRequestStatus(RequestStatus.Approved));
        //logging approve and remover
        adminService.deleteRequestByID(request_id);
    }


    private void removeDeniedRequest(String request_id, AdminService adminService) {
        Optional<GlobalRequests> getRequest = adminService.getRequestByID(request_id);
        getRequest.ifPresent(globalRequests -> globalRequests.setRequestStatus(RequestStatus.Denied));
        adminService.deleteRequestByID(request_id);
    }

    private void applyQosAndBugsRequest(String request_id, AdminService adminService, BugsAndQosService service) {
        GlobalRequests requests = adminService.getRequestByID(request_id).get();
        requests.setRequestStatus(RequestStatus.Approved);
        service.save_entity(requests);
        adminService.deleteRequestByID(request_id);
    }


    public boolean processOperation(SendRequestOperationDTO data, AdminService adminService, BugsAndQosService bugsAndQosService, ClientsService clientsService, JavaMailSender mailSender) {
        Optional<GlobalRequests> getById = adminService.getRequestByID(data.getRequest_id());
        if (getById.isPresent()) {
            GlobalRequests request = getById.get();
            switch (data.getOperation()) {
                case "APPLY":
                    if (request.getTag() == RequestTags.BUG || request.getTag() == RequestTags.CLIENT_QOS) {
                        applyQosAndBugsRequest(request.getId(), adminService, bugsAndQosService);
                        return true;
                    } else if (request.getTag() == RequestTags.PRODUCER_NEW) {
                        upgradeClientsRoleToProducer(request.getRequest_sender(), request.getRequest_sender().getMail(), clientsService, mailSender, request.getId(),adminService);
                        logAndRemoveApprovedRequestOrRoleUpdateRequest(request.getId(), adminService);
                        return true;
                    } else return false;
                case "DENY":
                    if (request.getTag() == RequestTags.BUG || request.getTag() == RequestTags.CLIENT_QOS) {
                        removeDeniedRequest(request.getId(), adminService);
                        return true;
                    } else if (request.getTag() == RequestTags.PRODUCER_NEW) {
                        denyClientsRequest(request.getId(), adminService, request.getRequest_sender().getMail(), mailSender);
                        return true;
                    } else return false;

                case "REMOVE":
                    removeDeniedRequest(request.getId(), adminService);
                    return true;
            }
        } else return false;
        return false;
    }


}
