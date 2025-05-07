package com.phatbee.cosmeticshopbackend.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("tama");
        System.out.println(hashedPassword);
    }
}
