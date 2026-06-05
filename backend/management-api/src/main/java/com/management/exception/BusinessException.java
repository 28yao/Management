package com.management.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 *
 * @author management
 * @date 2024-01-01
 */
public class BusinessException extends RuntimeException {

    private final int code;

    /**
     * 构造业务异常
     *
     * @param message 异常信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    /**
     * 构造业务异常
     *
     * @param code    错误码
     * @param message 异常信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
}
