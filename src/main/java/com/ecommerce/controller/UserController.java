package com.ecommerce.controller;

import com.ecommerce.config.JwtUtil;
import com.ecommerce.entity.Users;
import com.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class UserController {

    UserService userService;

    JwtUtil jwtUtil;

//    @GetMapping("/getAll")
//    public ResponseEntity<List<Users>> getAll(){
//
//    }

}
