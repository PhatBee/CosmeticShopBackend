package com.phatbee.cosmeticshopbackend.Service;

import com.phatbee.cosmeticshopbackend.Entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressesByUserId(Long userId);
}
