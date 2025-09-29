package com.ecommerce.controller;

import com.ecommerce.config.JwtUtil;
import com.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
