package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressValidation {
    public boolean isAddressNull(Address address) {
        if (address == null) return true;
        return address.getCountry() == null && address.getCity() == null && address.getAddress() == "" || address.getAddress() == null && address.getFlat() == "" || address.getFlat() == null && address.getPostcode() == "" || address.getPostcode() == null;
    }
}
