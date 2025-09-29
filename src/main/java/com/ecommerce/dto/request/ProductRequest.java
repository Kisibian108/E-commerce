package com.ecommerce.dto.request;

import lombok.Data;

@Data
public class ProductRequest {

    String name;
    String description;
    Long categoryId;
    Double price;
    Integer stock;
    String imageUrl;
}
