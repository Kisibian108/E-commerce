package com.ecommerce.mapper;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toCartItem(CartItemRequest cartItemRequest);

}
