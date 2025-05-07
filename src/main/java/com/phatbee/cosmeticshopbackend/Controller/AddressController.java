package com.phatbee.cosmeticshopbackend.Controller;

import com.phatbee.cosmeticshopbackend.Entity.Address;
import com.phatbee.cosmeticshopbackend.Service.Impl.AddressServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressServiceImpl addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        try{
            List<Address> addresses = addressService.getAddressesByUserId(userId);
            return ResponseEntity.ok(addresses);
        }
        catch(RuntimeException e){
           return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestParam Long userId, @RequestBody Address address) {
        try {
            Address savedAddress = addressService.addAddress(userId, address);
            return ResponseEntity.status(201).body(savedAddress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
