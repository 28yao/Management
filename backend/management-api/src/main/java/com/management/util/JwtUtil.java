package com.management.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 处理 Token 的生成、解析和验证
 *
 * @author management
 * @date 2024-01-01
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    /**
     * 初始化密钥
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Token
     *
     * @param userId  用户 ID
     * @param account 用户账号
     * @param role    用户角色
     * @return Token 字符串
     */
    public String generateToken(Long userId, String account, Integer role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("account", account);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 Token
     *
     * @param token Token 字符串
     * @return Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token Token 字符串
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 从 Token 中获取用户 ID
     *
     * @param token Token 字符串
     * @return 用户 ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.getSubject());
    }

    /**
     * 从 Token 中获取用户账号
     *
     * @param token Token 字符串
     * @return 用户账号
     */
    public String getAccountFromToken(String token) {
        Claims claims = parseToken(token);
        return (String) claims.get("account");
    }

    /**
     * 从 Token 中获取用户角色
     *
     * @param token Token 字符串
     * @return 用户角色
     */
    public Integer getRoleFromToken(String token) {
        Claims claims = parseToken(token);
        return (Integer) claims.get("role");
    }
}
