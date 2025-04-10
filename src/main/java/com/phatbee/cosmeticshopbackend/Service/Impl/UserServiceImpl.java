package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Config.OTPGenerator;
import com.phatbee.cosmeticshopbackend.Entity.User;
import com.phatbee.cosmeticshopbackend.Entity.UserOtp;
import com.phatbee.cosmeticshopbackend.Enum.Gender;
import com.phatbee.cosmeticshopbackend.Repository.UserOtpRepository;
import com.phatbee.cosmeticshopbackend.Repository.UserRepositoty;
import com.phatbee.cosmeticshopbackend.Service.UserService;
import com.phatbee.cosmeticshopbackend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private UserOtpRepository otpRepository;

    private static final int MAX_ATTEMPTS = 3;

    @Override
    public boolean authenticate(String username, String password) {
        return userRepositoty.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user .getPassword()) && user.isActivated())
                .orElse(false);
    }

    @Override
    public String registerUser(String username, String email, String password, String fullName, Date birthday, String gender, String phone, String imageUrl) {
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
        user.setImage(imageUrl);
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
    public String resendOtp1(String email) {
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

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepositoty.findByUsername(loginRequest.getUsername());

        if (!userOptional.isPresent()) {
            return new LoginResponse(false, "User not found", null);
        }

        User user = userOptional.get();

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Create a copy of user without the password for security
            User userResponse = new User();
            userResponse.setUserId(user.getUserId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setGender(user.getGender());
            userResponse.setImage(user.getImage());
            // Don't set the password!

            return new LoginResponse(true, "Login successful", userResponse);
        } else {
            return new LoginResponse(false, "Invalid password", null);
        }
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        // Check if username already exists
        if (userRepositoty.existsByUsername(request.getUsername())) {
            return new RegistrationResponse(false, "Username already exists");
        }

        // Check if email already exists
        if (userRepositoty.existsByEmail(request.getEmail())) {
            return new RegistrationResponse(false, "Email already registered");
        }

        // Generate OTP
        String otp = generateOtp();

        // Save OTP and user details temporarily
        UserOtp userOtp = otpRepository.findByEmail(request.getEmail()).orElse(new UserOtp());
        userOtp.setEmail(request.getEmail());
        userOtp.setOtp(otp);
        userOtp.setExpiryTime(LocalDateTime.now().plusMinutes(10)); // OTP valid for 10 minutes
        userOtp.setUsername(request.getUsername());
        userOtp.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        userOtp.setGender(request.getGender());
        otpRepository.save(userOtp);

        // Send OTP via email
        emailService.sendOtpEmail(request.getEmail(), otp);

        return new RegistrationResponse(true, "OTP sent to your email for verification");

    }

    @Override
    public RegistrationResponse verifyOtp(OtpVerificationRequest request) {
        Optional<UserOtp> userOtpOptional = otpRepository.findByEmail(request.getEmail());

        if (!userOtpOptional.isPresent()) {
            return new RegistrationResponse(false, "Invalid request or OTP expired");
        }

        UserOtp userOtp = userOtpOptional.get();

        // Check if OTP is expired
        if (LocalDateTime.now().isAfter(userOtp.getExpiryTime())) {
            otpRepository.delete(userOtp);
            return new RegistrationResponse(false, "OTP has expired. Please register again.");
        }

        // Verify OTP
        if (!userOtp.getOtp().equals(request.getOtp())) {
            return new RegistrationResponse(false, "Invalid OTP");
        }

        // Create new user
        User user = new User();
        user.setUsername(userOtp.getUsername());
        user.setPassword(userOtp.getPassword()); // Already encrypted
        user.setEmail(userOtp.getEmail());
        user.setGender(userOtp.getGender());

        // Save user
        userRepositoty.save(user);

        // Delete OTP entry
        otpRepository.delete(userOtp);

        return new RegistrationResponse(true, "Registration successful. Please login.");

    }

    private String generateOtp() {
        // Generate 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public RegistrationResponse resendOtp(String email) {
        Optional<UserOtp> userOtpOptional = otpRepository.findByEmail(email);

        if (!userOtpOptional.isPresent()) {
            return new RegistrationResponse(false, "No registration in progress for this email");
        }

        UserOtp userOtp = userOtpOptional.get();
        String newOtp = generateOtp();
        userOtp.setOtp(newOtp);
        userOtp.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        otpRepository.save(userOtp);

        // Send new OTP via email
        emailService.sendOtpEmail(email, newOtp);

        return new RegistrationResponse(true, "New OTP sent to your email");
    }

}
