package com.ecommerce.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemRequest {

    UUID productId;
    Integer quantity;
}
