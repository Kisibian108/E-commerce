package com.ecommerce.service;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PagedResponse;
import com.ecommerce.entity.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ProductService {

    ApiResponse<PagedResponse<Products>> getAllProducts(Pageable pageable, String name);

    ApiResponse<Products> save(ProductRequest productRequest, MultipartFile image) throws IOException;

    ApiResponse<Void> update(ProductRequest productRequest, UUID id);

    ApiResponse<Products> getProductById(UUID id);

    ApiResponse<Void> delete(UUID id);
}
