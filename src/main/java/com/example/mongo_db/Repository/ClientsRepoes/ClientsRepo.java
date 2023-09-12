package com.example.mongo_db.Repository.ClientsRepoes;

import com.example.mongo_db.Entity.Client.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepo extends MongoRepository<Client, String> {


    @Query(value = " {$or : [{'phone_number' : '?0'}, {'mail' :  '?1'},{'client_user_name' : '?2'}]}")
    Client doesUserExists(String phone_number, String mail, String client_user_name);


    //queries to create message with existed params ;)
    @Query(value = "{'client_user_name' :  '?0'}")
    Client doesUserNameExists(String client_user_name);

    @Query(value = "{'phone_number' :  '?0'}")
    Client doesUserPhoneNumberExists(String phone_number);

    @Query(value = "{'mail' :  '?0'}")
    Client doesUserMailExists(String mail);

    @Override
    List<Client> findAll();

}
