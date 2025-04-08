package com.phatbee.cosmeticshopbackend.Service;

import com.phatbee.cosmeticshopbackend.Enum.Gender;
import com.phatbee.cosmeticshopbackend.dto.LoginRequest;
import com.phatbee.cosmeticshopbackend.dto.LoginResponse;

import java.util.Date;

public interface UserService {
    public boolean authenticate(String username, String password);
    public String registerUser(String username, String email, String password, String fullName, Date birthday, Gender gender, String phone, String imageUrl);
    public String activateAccount(String email, String otp);
    public String resendOtp(String email);
    public String sendOtpForPasswordReset(String email);
    public String resetPassword(String email, String otp, String newPassword);
    public LoginResponse authenticate(LoginRequest loginRequest);


}
