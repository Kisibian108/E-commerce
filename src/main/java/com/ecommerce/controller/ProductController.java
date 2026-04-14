package com.ecommerce.controller;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(@PageableDefault Pageable pageable, @RequestParam(required = false) String name) {
        String nameSearch = (name == null || name.isBlank()) ? "%" : "%" + name.trim() + "%";
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        var products = productService.getAllProducts(sortedPageable, nameSearch);

        return ResponseEntity.ok(products);
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> insertProduct(@RequestBody ProductRequest productRequest) throws IOException {
        return ResponseEntity.ok(productService.save(productRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(productRequest, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.delete(id));
    }

}
