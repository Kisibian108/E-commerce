package com.ecommerce.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final int EXPIRE_MINUTES = 15;

    public OtpService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateOtp(String key) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(EXPIRE_MINUTES));
        return otp;
    }

    public boolean validateOtp(String key, String otpInput) {
        String otpStored = redisTemplate.opsForValue().get(key);
        if (otpStored == null) return false; // hết hạn
        boolean valid = otpStored.equals(otpInput);
        if (valid) redisTemplate.delete(key); // dùng xong xóa luôn
        return valid;
    }
}
