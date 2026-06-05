package com.management.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 登录响应 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@Builder
public class LoginResponse {

    /**
     * JWT Token
     */
    private String token;

    /**
     * 用户信息
     */
    private Map<String, Object> user;
}
