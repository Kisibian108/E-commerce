package com.ecommerce.controller;

import com.ecommerce.configuration.JwtUtil;
import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.entity.Users;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemController {

    CartItemService cartItemService;
    JwtUtil jwtUtil;
    UserRepository userRepository;

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody CartItemRequest cartItemRequest, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String userName = jwtUtil.getUsernameFromToken(token);
        Optional<Users> users = userRepository.findByUsername(userName);
        var result = cartItemService.insert(cartItemRequest,users.get().getId());
        return ResponseEntity.ok(result);
    }
}
