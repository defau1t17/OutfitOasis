package com.example.mongo_db.Service.Bucket;

import com.example.mongo_db.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Repository.ClientsRepoes.BucketRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BucketService implements EntityOperations {

    @Autowired
    private BucketRepo bucketRepo;

    public int findItemPosition(ArrayList<ClientShopItemDAO> items, ShopItem item) {
        for (ClientShopItemDAO dao : items) {
            if (dao.getItem().getId().equals(item.getId())) {
                return items.indexOf(dao);
            }
        }
        return -1;
    }

    @Override
    public void save_entity(Object obj) {
        bucketRepo.save((Bucket) obj);
    }

    @Override
    public void update_entity(Object obj) {
        bucketRepo.save((Bucket) obj);
    }

    @Override
    public void remove_entity(Object obj) {
        bucketRepo.delete((Bucket) obj);
    }
}
