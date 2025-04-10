package com.phatbee.cosmeticshopbackend.Entity;

import com.phatbee.cosmeticshopbackend.Enum.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    private Date birthDate;
    private String phone;
    private String gender;
    private String image;
    private String username;
    private String email;
    private String password;
    private boolean activated = false;
    private String otp; //One-time-password
    private int failedAttempts = 0;
    private LocalDateTime otpGeneratedAt;
}
