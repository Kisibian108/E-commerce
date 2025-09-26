package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {

    private String code;
    private LocalDateTime expireAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }
}
