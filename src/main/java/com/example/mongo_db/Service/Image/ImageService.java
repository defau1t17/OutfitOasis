package com.example.mongo_db.Service.Image;


import com.example.mongo_db.Entity.Client.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Service
public class ImageService {

    public Image buildDefaultImage() {
        File defaultImage = new File("src/main/resources/static/images/default-client-icon.jpg");
        Image image = new Image();
        try {
            image.setImage(Base64.getEncoder().encodeToString(Files.readAllBytes(defaultImage.toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

}
