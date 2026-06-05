package com.management.controller;

import com.management.common.ApiResponse;
import com.management.dto.request.LoginRequest;
import com.management.dto.response.LoginResponse;
import com.management.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 处理登录、登出、获取用户信息等认证相关请求
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 登录接口
     *
     * @param request 登录请求参数
     * @return 登录响应（包含 Token 和用户信息）
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success("登录成功", response);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> getCurrentUser() {
        Map<String, Object> userInfo = authService.getCurrentUser();
        return ApiResponse.success(userInfo);
    }

    /**
     * 登出接口
     *
     * @return 操作结果
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        authService.logout();
        return ApiResponse.success("登出成功", null);
    }

    /**
     * 修改密码
     *
     * @param request 密码修改请求
     * @return 操作结果
     */
    @PutMapping("/password")
    public ApiResponse<String> changePassword(@RequestBody Map<String, String> request) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        authService.changePassword(oldPassword, newPassword);
        return ApiResponse.success("密码修改成功", null);
    }
}
