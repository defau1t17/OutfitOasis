package com.example.mongo_db.DTO;

import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.Entity.Gender;
import com.example.mongo_db.Entity.Items.Item.Item;
import com.example.mongo_db.Entity.Items.models.Category;
import com.example.mongo_db.Entity.Items.models.Season;
import com.example.mongo_db.Entity.Items.models.Size;
import com.example.mongo_db.Entity.Producer.Producer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class NewProducerItemDTO extends Item {

    private String producerID;
//    private Image item_image;


}
