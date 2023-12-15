package com.example.mongo_db.Service.Producer;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.OutfitOasisConst;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProducerService implements EntityOperations {
    @Autowired
    private ProducerRepo producerRepo;

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


}
