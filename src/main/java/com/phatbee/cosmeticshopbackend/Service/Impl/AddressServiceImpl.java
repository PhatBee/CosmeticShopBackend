package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Entity.Address;
import com.phatbee.cosmeticshopbackend.Repository.AddressRepository;
import com.phatbee.cosmeticshopbackend.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByCustomerUserId(userId);
        if (addresses.isEmpty()) {
            throw new RuntimeException("No addresses found for user ID: " + userId);
        }
        return addresses;
    }
}
