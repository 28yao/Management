package com.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Notification;

/**
 * 通知服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface NotificationService {

    /**
     * 发送通知
     *
     * @param notification 通知
     */
    void send(Notification notification);

    /**
     * 获取员工的通知列表
     *
     * @param page  分页参数
     * @param empId 员工 ID
     * @return 通知分页
     */
    IPage<Notification> listMyNotifications(Page<Notification> page, Long empId);

    /**
     * 获取未读通知数量
     *
     * @param empId 员工 ID
     * @return 未读数量
     */
    long getUnreadCount(Long empId);

    /**
     * 标记通知已读
     *
     * @param id 通知 ID
     */
    void markAsRead(Long id);

    /**
     * 标记所有通知已读
     *
     * @param empId 员工 ID
     */
    void markAllAsRead(Long empId);
}
