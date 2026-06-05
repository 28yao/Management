package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class LoginRequest {

    /**
     * 登录账号
     */
    @NotBlank(message = "请输入账号")
    private String account;

    /**
     * 登录密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
}
