package com.phatbee.cosmeticshopbackend.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private String otp;
    private String email;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
