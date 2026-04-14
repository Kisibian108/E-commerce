package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PagedResponse;
import com.ecommerce.entity.Products;
import com.ecommerce.exception.AppException;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    String uploadDir = "uploads/";


    @Override
    public ApiResponse<PagedResponse<Products>> getAllProducts(Pageable pageable, String name) {
        Page<Products> page = productRepository.findAllProduct(pageable,name);
        return ApiResponse.success(new PagedResponse<>(page));
    }

    @Override
    public ApiResponse<Products> save(ProductRequest productRequest) {
        Products newProduct = productMapper.toProduct(productRequest);

        newProduct.setImageUrl(productRequest.getImageUrl());

        productRepository.save(newProduct);

        return ApiResponse.success(newProduct);
    }

    @Override
    public ApiResponse<Void> update(ProductRequest productRequest, UUID id) {
        Optional<Products> currentProduct = productRepository.findById(id);
        if (currentProduct.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productMapper.toUpdateProduct(currentProduct.get(), productRequest);
        if (productRequest.getImageUrl() != null && !productRequest.getImageUrl().isEmpty()) {
            currentProduct.get().setImageUrl(productRequest.getImageUrl());
        }
        productRepository.save(currentProduct.get());
        System.out.println("IMAGE FROM FE: " + productRequest.getImageUrl());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Products> getProductById(UUID id) {
        Optional<Products> currentProduct = productRepository.findById(id);
        if (currentProduct.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return ApiResponse.success(currentProduct.get());
    }

    @Override
    public ApiResponse<Void> delete(UUID id) {
        Optional<Products> currentProduct = productRepository.findById(id);
        if (currentProduct.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
        return ApiResponse.success();
    }
}
