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
    public ApiResponse<Products> save(ProductRequest productRequest, MultipartFile image) throws IOException {
        Products newProduct = productMapper.toProduct(productRequest);
        String imageUrl = uploadFile(image);
        newProduct.setImageUrl(imageUrl);
        productRepository.save(newProduct);
        return ApiResponse.success(newProduct);
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // Tạo thư mục nếu chưa có
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Tạo tên file duy nhất
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Lưu file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Trả về đường dẫn public
        return "/files/" + fileName;
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
