package com.ecommerce.mapper;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.entity.Products;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Products toProduct(ProductRequest productRequest);

    void toUpdateProduct(@MappingTarget Products products, ProductRequest productRequest);

}
