package com.example.mongo_db.Service.Items;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Size;
import com.example.mongo_db.Entity.OutfitOasisConst;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatalogService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private ProducerRepo producerRepo;

    private static final int PAGE_SIZE_ITEMS = 10;


    public List<Category> findAllCategories() {
        return Arrays.asList(Category.values());
    }

    public List<ShopItem> findAllItems() {
        return itemRepo.findAll();
    }


    public Page<ShopItem> findByPage(int current_page) {
        PageRequest pageRequest = PageRequest.of(current_page, OutfitOasisConst.DEFAULT_PAGE_SIZE);
        return itemRepo.findAll(pageRequest);
    }

    public Page<ShopItem> findAllItemsByCategory(String category, int current_page) {
        PageRequest pageRequest = PageRequest.of(current_page, PAGE_SIZE_ITEMS);
        return itemRepo.findAllItemsByCategory(category, pageRequest);
    }

    public ShopItem findItemById(String id) {
        return itemRepo.findShopItemById(id).orElse(null);
    }

//    public void generate(Client client) {
//        ArrayList<ShopItem> items = new ArrayList<>();
//        ShopItem shopItem = new ShopItem();
//
//        Image image = new Image();
//        image.setImage("");
//
//        Producer producer = new Producer();
//        producer.setProducer_brand_name("Nike");
//        producer.setProducer_country("China");
//        producer.setClient(client);
//        producerRepo.save(producer);
//
//
//        Dress dress = new Dress();
//        dress.setName("Dress");
//        dress.setCategory(Category.Dress);
//        dress.setPrice(4342);
//        dress.setDescription("test desc");
//        dress.setCountry_producer("China");
//        dress.setGender(Gender.Woman);
//        dress.setColor("black");
//        dress.setSize(Size.S);
//        dress.setClothes_composition((HashMap<Composition, Integer>) new HashMap<>().put(Composition.Chiffon, 43));
//        dress.setHeight(342);
//        dress.setItem_image(image);
//        shopItem.setItem(dress);
//
//        System.out.println(producer.getId());
//        shopItem.setProducer_id(producer.getId());
//
//
//        items.add(shopItem);
//        items.add(shopItem);
//        items.add(shopItem);
//
//
//        producer.setProducer_items(items);
//
//        itemRepo.saveAll(items);
//        producerRepo.save(producer);
//
//
//    }

}
