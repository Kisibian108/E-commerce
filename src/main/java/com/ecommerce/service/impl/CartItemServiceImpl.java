package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Products;
import com.ecommerce.entity.Users;
import com.ecommerce.mapper.CartItemMapper;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemServiceImpl implements CartItemService {

    CartItemRepository cartItemRepository;
    CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ApiResponse<CartItem> insert(CartItemRequest cartItemRequest, UUID userId) {
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequest);
//        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemRequest.getProductId());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setUserId(userId);
        cartItemRepository.save(cartItem);
        return ApiResponse.success(cartItem);
    }
}
