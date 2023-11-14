package com.example.mongo_db.Service.Producer;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void update_entity(Object obj) {
        producerRepo.save((Producer) obj);

    }
    @Override
    public void remove_entity(Object obj) {
        producerRepo.delete((Producer) obj);
    }
}
