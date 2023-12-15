package com.example.mongo_db.Service.Requests;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.EntityOperations;
import com.example.mongo_db.Service.Items.CatalogService;
import com.example.mongo_db.Service.MessageSenderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RequestsService implements EntityOperations {

    @Autowired
    private RequestsRepo requestsRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private MessageSenderService messageSenderService;

    public boolean isClientOnModeration(Client client) {
        Optional<GlobalRequests> client_in_request = requestsRepo.isClientInRequestList(client.getId(), RequestTags.PRODUCER_NEW);
        if (client_in_request.isPresent()) {
            return true;
        } else
            return false;
    }

    public boolean validateClientBeforeAdd(HttpServletRequest request) {
        return isClientOnModeration((Client) request.getSession().getAttribute("global_client"));
    }

    public String saveNewProducerRequest(GlobalRequests<RequestData> producer_request, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        producer_request.setRequest_sender(client);
        save_entity(producer_request);
        clientsService.updateGlobalClient(client, request.getSession());
        sendMessage(producer_request.getData_inf().getRequest_producer_mail());
        return producer_request.getId();
    }

    public String saveNewReport(GlobalRequests<String> bug_request, HttpServletRequest request) {
        bug_request.setRequest_sender((Client) request.getSession().getAttribute("global_client"));
        save_entity(bug_request);
        return bug_request.getId();
    }

    public boolean isClientAlreadyProducer(Client client) {
        return client.getRole().equals(Role.ROLE_PRODUCER);
    }


    public void sendMessage(String producer_mail) {
        messageSenderService.sendClientNotificationAboutRequestStatus(producer_mail, javaMailSender);
    }

    public String getProducerStatus(Client client) {
        if (isClientOnModeration(client))
            return "ON_MODERATION";
        else if (isClientAlreadyProducer(client))
            return "PRODUCER";
        else return "CLIENT";
    }


    @Override
    public void save_entity(Object obj) {
        requestsRepo.save((GlobalRequests<?>) obj);

    }

    @Override
    public void update_entity(Object obj) {
        requestsRepo.save((GlobalRequests<?>) obj);

    }

    @Override
    public void remove_entity(Object obj) {
        requestsRepo.delete((GlobalRequests<?>) obj);

    }
}
