package com.example.mongo_db.Service.Image;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Repository.ClientsRepoes.ImagesRepo;
import com.example.mongo_db.Service.Clients.ClientsService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImagesRepo imagesRepo;

    @Autowired
    private ClientsService service;

    @Transactional
    public String uploadPhoto(MultipartFile file, Client client) throws IOException {
        Image image = client.getClient_image();
        image.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        imagesRepo.save(image);
        client.setClient_image(image);
        service.updateClient(client);
        return image.getId();
    }

    public Image findImage(String id) {
        Optional<Image> byId = imagesRepo.findById(id);
        return byId.orElse(null);
    }

}
