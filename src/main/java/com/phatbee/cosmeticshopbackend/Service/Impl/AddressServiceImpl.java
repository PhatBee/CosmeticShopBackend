package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Entity.Address;
import com.phatbee.cosmeticshopbackend.Entity.User;
import com.phatbee.cosmeticshopbackend.Repository.AddressRepository;
import com.phatbee.cosmeticshopbackend.Repository.UserRepositoty;
import com.phatbee.cosmeticshopbackend.Service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepositoty userRepository;

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByCustomerUserId(userId);
        if (addresses.isEmpty()) {
            throw new RuntimeException("No addresses found for user ID: " + userId);
        }
        return addresses;
    }

    @Override
    @Transactional
    public Address addAddress(Long userId, Address address) {
        // Validate user
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));



        // Set the customer relationship
        address.setCustomer(user);



        // Save the address
        return addressRepository.save(address);
    }
}
