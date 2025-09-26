package com.ecommerce.service;

import com.ecommerce.dto.request.ChangePasswordRequest;
import com.ecommerce.dto.request.ResetPasswordRequest;
import com.ecommerce.dto.request.UserRequest;
import com.ecommerce.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    ApiResponse<Object> register(@RequestBody UserRequest userRequest);

    ApiResponse<Object> forgetPass(@RequestBody ChangePasswordRequest changePasswordRequest);

    ApiResponse<Object> changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest);
}
