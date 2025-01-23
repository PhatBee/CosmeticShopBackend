package com.phatbee.cosmeticshopbackend.Controller;

import com.phatbee.cosmeticshopbackend.Service.Impl.UserServiceImpl;
import com.phatbee.cosmeticshopbackend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if(userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String response = userService.registerUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getFullName(), registerRequest.getBirthDate(), registerRequest.getGender(), registerRequest.getPhone(), registerRequest.getImageUrl());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody OtpRequest otpRequest) {
        String response = userService.activateAccount(otpRequest.getEmail(), otpRequest.getOtp());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody ResendOtpRequest request) {
        String response = userService.resendOtp(request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String response = userService.sendOtpForPasswordReset(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String response = userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getOtp(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok(response);
    }
}
