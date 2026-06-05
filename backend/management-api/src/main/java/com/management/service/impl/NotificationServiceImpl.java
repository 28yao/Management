package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Notification;
import com.management.mapper.NotificationMapper;
import com.management.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * 通知服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void send(Notification notification) {
        notificationMapper.insert(notification);
    }

    @Override
    public IPage<Notification> listMyNotifications(Page<Notification> page, Long empId) {
        return notificationMapper.selectPage(page,
                new LambdaQueryWrapper<Notification>()
                        .eq(Notification::getEmpId, empId)
                        .orderByDesc(Notification::getCreatedAt)
        );
    }

    @Override
    public long getUnreadCount(Long empId) {
        return notificationMapper.selectCount(
                new LambdaQueryWrapper<Notification>()
                        .eq(Notification::getEmpId, empId)
                        .eq(Notification::getIsRead, 0)
        );
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationMapper.selectById(id);
        if (notification != null) {
            notification.setIsRead(1);
            notificationMapper.updateById(notification);
        }
    }

    @Override
    public void markAllAsRead(Long empId) {
        notificationMapper.update(null,
                new LambdaUpdateWrapper<Notification>()
                        .eq(Notification::getEmpId, empId)
                        .eq(Notification::getIsRead, 0)
                        .set(Notification::getIsRead, 1)
        );
    }
}
