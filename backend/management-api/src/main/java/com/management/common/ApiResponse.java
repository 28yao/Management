package com.management.common;

import lombok.Data;

/**
 * 统一响应包装类
 *
 * @param <T> 数据类型
 * @author management
 * @date 2024-01-01
 */
@Data
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应（无数据）
     *
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "success", null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    /**
     * 成功响应（带消息和数据）
     *
     * @param message 响应消息
     * @param data    响应数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * 失败响应
     *
     * @param message 错误信息
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message, null);
    }

    /**
     * 失败响应（带错误码）
     *
     * @param code    错误码
     * @param message 错误信息
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
