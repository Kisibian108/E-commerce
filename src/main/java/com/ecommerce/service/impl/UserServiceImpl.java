package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ChangePasswordRequest;
import com.ecommerce.dto.request.ResetPasswordRequest;
import com.ecommerce.dto.request.UserRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.OtpResponse;
import com.ecommerce.entity.Users;
import com.ecommerce.exception.AppException;
import com.ecommerce.exception.ErrorCode;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    EmailService emailService;
    OtpService otpService;

    @Override
    public ApiResponse<Object> register(UserRequest userRequest) {
        Optional<Users> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Users users = userMapper.toUser(userRequest);
        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(users);

        return ApiResponse.success(users);

    }

    @Override
    public ApiResponse<Object> forgetPass(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByEmail(changePasswordRequest.getEmail());
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        String key = "OTP:" + user.get().getId();
        String otp = otpService.generateOtp(key);
        emailService.sendEmail(user.get().getEmail(), "OTP confirmation", "Mã opt của bạn là " + otp);

        return ApiResponse.success(otp);
    }

    @Override
    public ApiResponse<Object> changePassword(ResetPasswordRequest resetPasswordRequest) {
        Users user = userRepository.findByEmail(resetPasswordRequest.email())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String key = "OTP:" + user.getId();
        boolean valid = otpService.validateOtp(key, resetPasswordRequest.otp());
        if (!valid) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        userRepository.save(user);
        return ApiResponse.success(user);
    }
}
