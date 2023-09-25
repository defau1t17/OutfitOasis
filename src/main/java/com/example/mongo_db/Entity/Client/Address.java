package com.example.mongo_db.Entity.Client;

import com.example.mongo_db.Entity.Parse.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Address {

    private Country country;
    @NotEmpty
    @NotBlank
    private String city;
    @NotEmpty
    @NotBlank
    private String address;
    @NotEmpty
    @NotBlank
    private String flat;
    @NotEmpty
    @NotBlank
    private String postcode;

}
