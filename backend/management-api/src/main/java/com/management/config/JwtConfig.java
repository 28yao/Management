package com.management.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间（毫秒）
     */
    private Long expiration;
}
