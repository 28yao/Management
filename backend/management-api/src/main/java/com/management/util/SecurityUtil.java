package com.management.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security 工具类
 * 获取当前登录用户信息
 *
 * @author management
 * @date 2024-01-01
 */
public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * 获取当前登录用户 ID
     *
     * @return 用户 ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.valueOf(authentication.getName());
        }
        return null;
    }

    /**
     * 获取当前登录用户角色
     *
     * @return 角色名称
     */
    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .orElse(null);
        }
        return null;
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return 是否是管理员
     */
    public static boolean isAdmin() {
        String role = getCurrentUserRole();
        return "ROLE_ADMIN".equals(role);
    }
}
