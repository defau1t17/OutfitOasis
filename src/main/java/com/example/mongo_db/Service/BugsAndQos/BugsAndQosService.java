package com.example.mongo_db.Service.BugsAndQos;


import com.example.mongo_db.Entity.BugsAndQos.BugsAndQOS;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Repository.BugsAndQosRepoes.BugsAndQosRepository;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BugsAndQosService implements EntityOperations {

    @Autowired
    private BugsAndQosRepository repository;

    @Override
    public void save_entity(Object obj) {
        GlobalRequests obj1 = (GlobalRequests) obj;
        BugsAndQOS bugsAndQOS = new BugsAndQOS();
        bugsAndQOS.setRequest(obj1);
        repository.save(bugsAndQOS);
    }

    public String findNewEntityByOldID(String oldID) {
        return repository.findByRequestId(oldID).get().getId();
    }

    @Override
    public void update_entity(Object obj) {

        repository.save((BugsAndQOS) obj);
    }

    @Override
    public void remove_entity(Object obj) {
        repository.delete((BugsAndQOS) obj);
    }


}
