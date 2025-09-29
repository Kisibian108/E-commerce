package com.ecommerce.repository;

import com.ecommerce.entity.Products;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Products, UUID> {

    @Query(value = "select p from Products p where p.name like :name")
    Page<Products> findAllProduct(Pageable pageable,@Param("name") String name);
}
