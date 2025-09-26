package com.ecommerce.controller;

import com.ecommerce.config.JwtUtil;
import com.ecommerce.dto.request.ChangePasswordRequest;
import com.ecommerce.dto.request.LoginRequest;
import com.ecommerce.dto.request.ResetPasswordRequest;
import com.ecommerce.dto.request.UserRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.exception.AppException;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    JwtUtil jwtUtil;

    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

            return ApiResponse.builder().result((jwtUtil.generateToken(loginRequest.username()))).code(1000).build();
        } catch (AuthenticationException e) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest userRequest) {
         var result = userService.register(userRequest);
         return ResponseEntity.ok(result);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgetPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
           var result =  userService.forgetPass(changePasswordRequest);
           return ResponseEntity.ok(result);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        var result =  userService.changePassword(resetPasswordRequest);
        return ResponseEntity.ok(result);
    }

}
