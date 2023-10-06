package com.example.mongo_db.DAO;

import com.example.mongo_db.Entity.Items.Item.ShopItem;
import lombok.Data;

@Data
public class ClientShopItemDAO {

    private ShopItem item;

    private int quantity;


}
