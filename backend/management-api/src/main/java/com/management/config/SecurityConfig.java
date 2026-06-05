package com.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 *
 * @author management
 * @date 2024-01-01
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 白名单路径 - 无需认证
     */
    private static final String[] WHITE_LIST = {
            "/auth/login",
            "/auth/register",
            "/error"
    };

    /**
     * 安全过滤器链配置
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF
                .csrf().disable()
                // 无状态会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 请求授权
                .authorizeRequests()
                // 白名单放行
                .antMatchers(WHITE_LIST).permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
                .and()
                // 禁用 form 登页
                .formLogin().disable()
                // 禎用 httpBasic
                .httpBasic().disable();

        return http.build();
    }

    /**
     * 密码编码器
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
