package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Config.OTPGenerator;
import com.phatbee.cosmeticshopbackend.Entity.User;
import com.phatbee.cosmeticshopbackend.Enum.Gender;
import com.phatbee.cosmeticshopbackend.Repository.UserRepositoty;
import com.phatbee.cosmeticshopbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    private static final int MAX_ATTEMPTS = 3;

    @Override
    public boolean authenticate(String username, String password) {
        return userRepositoty.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user .getPassword()) && user.isActivated())
                .orElse(false);
    }

    @Override
    public String registerUser(String username, String email, String password, String fullName, Date birthday, Gender gender, String phone, String imageUrl) {
        if (userRepositoty.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if (userRepositoty.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already registered");
        }
        String otp = OTPGenerator.generateOTP();
        emailService.sendOtp(email, otp);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setGender(gender);
        user.setPhone(phone);
        user.setImageUrl(imageUrl);
        user.setOtp(otp);
        user.setOtpGeneratedAt(LocalDateTime.now());
        userRepositoty.save(user);

        return "Registration successful. Please check your email for the OTP.";
    }

    @Override
    public String activateAccount(String email, String otp) {
        User user = userRepositoty.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid Exception, No user found"));

        if (user.isActivated()){
            throw new RuntimeException("User is already activated");
        }

        if (!otp.equals(user.getOtp())) {
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            userRepositoty.save(user);

            if (user.getFailedAttempts() >= MAX_ATTEMPTS) {
                userRepositoty.delete(user);
                throw new RuntimeException("Too many attempts, Account registration has been canceled");
            }
            throw new RuntimeException("Invalid OTP. Please try again");
        }

        user.setActivated(true);
        user.setOtp(null);
        user.setFailedAttempts(0);
        userRepositoty.save(user);
        return "Activated successful";
    }

    @Override
    public String resendOtp(String email) {
        User user = userRepositoty.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isActivated()) {
            throw new RuntimeException("Account is already activated");
        }

        LocalDateTime now = LocalDateTime.now();
        if (user.getOtpGeneratedAt() != null && user.getOtpGeneratedAt().plusSeconds(30).isAfter(now)) {
            throw new RuntimeException("Please wait 30 seconds before requesting a new OTP");
        }

        String newOtp = OTPGenerator.generateOTP();
        user.setOtp(newOtp);
        user.setOtpGeneratedAt(now);
        userRepositoty.save(user);

        emailService.sendOtp(user.getEmail(), newOtp);
        return "A new OTP has been sent to your email.";
    }

    @Override
    public String sendOtpForPasswordReset(String email) {
        User user = userRepositoty.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActivated()) {
            throw new RuntimeException("User is not activated, Please contact administrator");
        }

        String otp = OTPGenerator.generateOTP();
        emailService.sendOtp(email, otp);
        user.setOtp(otp);
        user.setOtpGeneratedAt(LocalDateTime.now());
        userRepositoty.save(user);
        return "A new OTP has been sent to your email.";

    }

    @Override
    public String resetPassword(String email, String otp, String newPassword) {
        User user = userRepositoty.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid Exception, No user found"));

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again");
        }

        LocalDateTime now = LocalDateTime.now();
        if (user.getOtpGeneratedAt() != null && user.getOtpGeneratedAt().plusSeconds(30).isAfter(now)) {
            throw new RuntimeException("Please wait 30 seconds before requesting a new OTP");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        userRepositoty.save(user);

        return "Password reset successful";
    }
}
