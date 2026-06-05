package com.management.service;

import com.management.dto.request.LoginRequest;
import com.management.dto.response.LoginResponse;

import java.util.Map;

/**
 * 认证服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    Map<String, Object> getCurrentUser();

    /**
     * 用户登出
     */
    void logout();

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(String oldPassword, String newPassword);
}
