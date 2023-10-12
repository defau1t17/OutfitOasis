package com.example.mongo_db.DAO;

import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Producer.Producer;
import lombok.Data;

@Data
public class ClientShopItemDAO {

    private ShopItem item;

    private int quantity;

    private Producer producer;

}
