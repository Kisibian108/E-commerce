package com.ecommerce.dto.request;

public record ResetPasswordRequest(String email,String otp, String newPassword) {
}
