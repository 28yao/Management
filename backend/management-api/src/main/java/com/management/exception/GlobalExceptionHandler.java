package com.management.exception;

import com.management.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理系统中的各种异常，返回标准响应格式
 *
 * @author management
 * @date 2024-01-01
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param e       业务异常
     * @param request 请求对象
     * @return 统一响应
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 统一响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败: {}", message);
        return ApiResponse.error(400, message);
    }

    /**
     * 处理绑定异常
     *
     * @param e 绑定异常
     * @return 统一响应
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handleBindException(BindException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数绑定失败: {}", message);
        return ApiResponse.error(400, message);
    }

    /**
     * 处理认证失败异常
     *
     * @param e 认证异常
     * @return 统一响应
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Void> handleBadCredentialsException(BadCredentialsException e) {
        log.warn("认证失败: {}", e.getMessage());
        return ApiResponse.error(401, "账号或密码错误");
    }

    /**
     * 处理权限不足异常
     *
     * @param e 权限异常
     * @return 统一响应
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("权限不足: {}", e.getMessage());
        return ApiResponse.error(403, "权限不足");
    }

    /**
     * 处理其他未知异常
     *
     * @param e       异常
     * @param request 请求对象
     * @return 统一响应
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return ApiResponse.error(500, "系统内部错误");
    }
}
