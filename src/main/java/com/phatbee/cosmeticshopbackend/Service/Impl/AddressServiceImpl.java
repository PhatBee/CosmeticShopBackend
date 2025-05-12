package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Entity.Address;
import com.phatbee.cosmeticshopbackend.Entity.User;
import com.phatbee.cosmeticshopbackend.Repository.AddressRepository;
import com.phatbee.cosmeticshopbackend.Repository.UserRepository;
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
    UserRepository userRepository;

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByCustomerUserId(userId);
        if (addresses.isEmpty()) {
            throw new RuntimeException("Address is not found");
        }
        return addresses;
    }

    @Override
    @Transactional
    public Address addAddress(Long userId, Address address) {
        // Validate user
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the customer relationship
        address.setCustomer(user);

        if (address.isDefault()) {
            // Đặt các địa chỉ khác thành không mặc định
            addressRepository.findByCustomerUserId(userId).forEach(addr -> {
                addr.setDefault(false);
                addressRepository.save(addr);
            });
        }

        // Save the address
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Long userId, Address updatedAddress) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(updatedAddress.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Kiểm tra xem địa chỉ có thuộc về người dùng không
        if (!address.getCustomer().getUserId().equals(userId)) {
            throw new RuntimeException("Address not found");
        }

        // Cập nhật thông tin địa chỉ
        address.setReceiverName(updatedAddress.getReceiverName());
        address.setReceiverPhone(updatedAddress.getReceiverPhone());
        address.setAddress(updatedAddress.getAddress());
        address.setProvince(updatedAddress.getProvince());
        address.setDistrict(updatedAddress.getDistrict());
        address.setWard(updatedAddress.getWard());


        // Xử lý địa chỉ mặc định
        if (updatedAddress.isDefault()) {
            // Đặt các địa chỉ khác thành không mặc định
            addressRepository.findByCustomerUserId(userId).forEach(addr -> {
                if (!addr.getAddressId().equals(address.getAddressId())) {
                    addr.setDefault(false);
                    addressRepository.save(addr);
                }
            });
            address.setDefault(true);
        } else {
            address.setDefault(false);
        }

        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Kiểm tra xem địa chỉ có thuộc về người dùng không
        if (!address.getCustomer().getUserId().equals(userId)) {
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(addressId);
    }

    @Override
    public Address getDefaultAddress(Long userId) {
        List<Address> addresses = addressRepository.findByCustomerUserId(userId);
        return addresses.stream()
                .filter(Address::isDefault)
                .findFirst()
                .orElse(null); // Return null if no default address is found
    }
}
