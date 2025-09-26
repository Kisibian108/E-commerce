package com.ecommerce.mapper;

import com.ecommerce.dto.request.UserRequest;
import com.ecommerce.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toUser (UserRequest userRequest);
}
