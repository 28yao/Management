package com.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.common.ApiResponse;
import com.management.entity.Notification;
import com.management.service.NotificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 获取我的通知列表
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<IPage<Notification>> myNotifications(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Long empId = Long.valueOf(authentication.getName());
        Page<Notification> page = new Page<>(current, size);
        return ApiResponse.success(notificationService.listMyNotifications(page, empId));
    }

    /**
     * 获取未读通知数量
     */
    @GetMapping("/my/unread-count")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Map<String, Long>> unreadCount(Authentication authentication) {
        Long empId = Long.valueOf(authentication.getName());
        Map<String, Long> result = new HashMap<>();
        result.put("count", notificationService.getUnreadCount(empId));
        return ApiResponse.success(result);
    }

    /**
     * 标记通知已读
     */
    @PutMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ApiResponse.success("已标记为已读", null);
    }

    /**
     * 标记所有通知已读
     */
    @PutMapping("/read-all")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<String> markAllAsRead(Authentication authentication) {
        Long empId = Long.valueOf(authentication.getName());
        notificationService.markAllAsRead(empId);
        return ApiResponse.success("已全部标记为已读", null);
    }
}
