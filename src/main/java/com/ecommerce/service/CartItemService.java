package com.ecommerce.service;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.entity.CartItem;

import java.util.UUID;

public interface CartItemService {

    ApiResponse<CartItem> insert(CartItemRequest cartItemRequest, UUID userId);
}
