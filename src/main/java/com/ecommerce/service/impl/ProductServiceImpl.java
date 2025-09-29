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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public ApiResponse<PagedResponse<Products>> getAllProducts(Pageable pageable, String name) {
        Page<Products> page = productRepository.findAllProduct(pageable,name);
        return ApiResponse.success(new PagedResponse<>(page));
    }

    @Override
    public ApiResponse<Products> save(ProductRequest productRequest) {
        Products newProduct = productMapper.toProduct(productRequest);
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
        productRepository.save(currentProduct.get());

        return ApiResponse.success();
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
