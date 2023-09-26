package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Address;

public class CheckForAddress {

    public static boolean isAddressNull(Address address) {
        return address.getCountry() == null && address.getCity() == null && address.getAddress() == "" && address.getFlat() == "" && address.getPostcode() == "";
    }

}
