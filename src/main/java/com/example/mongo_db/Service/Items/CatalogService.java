package com.example.mongo_db.Service.Items;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Items.Types.Clothes.Dress.Dress;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Items.models.Composition;
import com.example.mongo_db.Entity.Items.models.Size;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Category> findAllCategories() {
        return Arrays.asList(Category.values());
    }

    public List<ShopItem> findAllItems() {
//        Optional<Client> byId = clientsRepo.findById("65278509984ad2679746339e");
//        generate(byId.get());
        return itemRepo.findAll();
    }

    public List<ShopItem> findAllItemsByCategory(String category) {
        return itemRepo.findAllByItem_Category(category);
    }

    public ShopItem findItemById(String id) {
        return itemRepo.findShopItemById(id).orElse(null);
    }

    public void generate(Client client) {
        ArrayList<ShopItem> items = new ArrayList<>();
        ShopItem shopItem = new ShopItem();

        Image image = new Image();
        image.setImage("");

        Producer producer = new Producer();
        producer.setProducer_brand_name("Nike");
        producer.setProducer_country("China");
        producer.setClient(client);
        producerRepo.save(producer);



        Dress dress = new Dress();
        dress.setName("Dress");
        dress.setCategory(Category.Dress);
        dress.setPrice(4342);
        dress.setDescription("test desc");
        dress.setCountry_producer("China");
        dress.setGender(Gender.Woman);
        dress.setColor("black");
        dress.setSize(Size.S);
        dress.setClothes_composition((HashMap<Composition, Integer>) new HashMap<>().put(Composition.Chiffon, 43));
        dress.setHeight(342);
        dress.setItem_image(image);
        shopItem.setItem(dress);

        System.out.println(producer.getId());
        shopItem.setProducer_id(producer.getId());


        items.add(shopItem);
        items.add(shopItem);
        items.add(shopItem);


        producer.setProducer_items(items);

        itemRepo.saveAll(items);
        producerRepo.save(producer);


    }

}
