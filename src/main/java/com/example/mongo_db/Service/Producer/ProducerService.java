package com.example.mongo_db.Service.Producer;

import com.example.mongo_db.DTO.NewProducerItemDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.OutfitOasisConst;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import com.example.mongo_db.RestExceptions.NewItemValidationException;
import com.example.mongo_db.Service.EntityOperations;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProducerService implements EntityOperations {
    @Autowired
    private ProducerRepo producerRepo;

    @Autowired
    private RequestsService requestsService;

    private static final String GLOBAL_CLIENT = "global_client";

    public Optional<Producer> findProducerByID(String id) {
        return producerRepo.findById(id);
    }

    @Override
    public void save_entity(Object obj) {
        producerRepo.save((Producer) obj);
    }

    public Page<ShopItem> getPageProducerItems(int page, String producerID) {
        PageRequest pageRequest = PageRequest.of(page, OutfitOasisConst.DEFAULT_PAGE_SIZE);
        return producerRepo.findAllShopItemsByProducerID(producerID, pageRequest);
    }

    public Producer findProducerByClientsID(String clientID) {
        Optional<Producer> optionalProducer = producerRepo.findByClientID(clientID);
        return optionalProducer.orElse(null);
    }


    @Override
    public void update_entity(Object obj) {
        producerRepo.save((Producer) obj);
    }

    @Override
    public void remove_entity(Object obj) {
        producerRepo.delete((Producer) obj);
    }


    public void generateProducerFromRequestAndSave(RequestData requestData, Client client) {
        ArrayList<ShopItem> items = new ArrayList<>();
        Producer newProducer = Producer.builder()
                .brandName(requestData.getRequest_brand_name())
                .country(requestData.getRequest_producer_country())
                .producerEmail(requestData.getRequest_producer_mail())
                .client(client)
                .producedItems(items)
                .build();
        save_entity(newProducer);
    }

    public String createNewRequestForNewItem(NewProducerItemDTO newProducerItemDTO) throws NewItemValidationException {
        ShopItem shopItem = new ShopItem();
        Producer producer = null;
        if (validateNewProduct(newProducerItemDTO)) {
            GlobalRequests globalRequests = new GlobalRequests();
            globalRequests.setTag(RequestTags.PRODUCER_ADD_ITEM);
            globalRequests.setRequest_sender(producerRepo.findById(newProducerItemDTO.getProducerID()).get().getClient());
            globalRequests.setData_inf(newProducerItemDTO);
            requestsService.save_entity(globalRequests);
            return globalRequests.getId();
        }
        throw new NewItemValidationException();
    }

    public boolean validateNewProduct(NewProducerItemDTO newProducerItemDTO) {
        if (newProducerItemDTO == null)
            return false;
        if (newProducerItemDTO.getName() == null || newProducerItemDTO.getName().isEmpty())
            return false;
        if (newProducerItemDTO.getCategory() == null)
            return false;
        if (newProducerItemDTO.getPrice() < 0)
            return false;
        if (newProducerItemDTO.getDescription() == null || newProducerItemDTO.getDescription().isEmpty())
            return false;
        if (newProducerItemDTO.getSeason() == null)
            return false;
        if (newProducerItemDTO.getSize() == null)
            return false;
        if (newProducerItemDTO.getGender() == null)
            return false;
        if (newProducerItemDTO.getComposition() == null)
            return false;
        return true;
    }

    public void createSession(Producer producer, HttpSession request) {
        request.setAttribute(GLOBAL_CLIENT, producer);

    }

}
