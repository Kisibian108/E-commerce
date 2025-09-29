package com.ecommerce.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_USERNAME_OR_PASSWORD(1005, "Invalid username or password"),
    INVALID_KEY(1001, "Uncategorized error"),
    USER_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),
    USER_NOT_FOUND(1005, "User not found"),
    INVALID_OTP(1006, "Invalid OTP"),
    ROLE_NOT_FOUND(1006,"Role not found" ),
    PRODUCT_NOT_FOUND(1007, "Product not found" ),;

    private final int code;
    private final String message;
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
