package com.ecommerce.controller;

import com.ecommerce.config.JwtUtil;
import com.ecommerce.dto.request.LoginRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.exception.AppException;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            return ApiResponse.builder().result((jwtUtil.generateToken(loginRequest.getUsername()))).code(1000).build();
        } catch (AuthenticationException e) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);

        }
    }
}
