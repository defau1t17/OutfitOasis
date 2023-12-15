package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "clients")
public class Client extends User {

    @Id
    private String id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String mail;

    @NotNull
    @NotBlank
    @NotEmpty
    private String phone_number;

    @NotNull
    @NotBlank
    @NotEmpty
    private String client_user_name;

    @NotNull
    @NotBlank
    @NotEmpty
    private String client_password;

    @Field(name = "client_address")
    private Address address;

    @Field(name = "client_bucket")
    private Bucket bucket;

    @Field(name = "client_image")
    private Image client_image;

    @Field(name = "role")
    private Role role = Role.ROLE_CLIENT;

    private LocalDate registrationDate;

    private LocalDateTime lastVisit;


    public Client updateClient(Client updatedClient, boolean isAddressEmpty) {
        if (updatedClient.getName() != null && updatedClient.getName().trim() != "") {
            this.setName(updatedClient.getName());
        }
        if (updatedClient.getSecond_name() != null && updatedClient.getSecond_name().trim() != "") {
            this.setSecond_name(updatedClient.getSecond_name());
        }
        if (updatedClient.getAge() != 0 && updatedClient.getAge() >= 12) {
            this.setAge(updatedClient.getAge());
        }
        if (updatedClient.getPhone_number() != null && updatedClient.getPhone_number().trim() != "") {
            this.setPhone_number(updatedClient.getPhone_number());
        }
        if (updatedClient.getAddress() != null && !isAddressEmpty) {
            this.setAddress(updatedClient.getAddress());
        } else if (updatedClient.getAddress() == null && isAddressEmpty) {
            this.setAddress(null);
        }
        return this;
    }


}
