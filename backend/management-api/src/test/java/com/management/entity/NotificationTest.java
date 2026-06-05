package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Notification 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class NotificationTest {

    @Test
    void testCreateNotification() {
        Notification notif = new Notification();
        notif.setId(1L);
        notif.setEmpId(1L);
        notif.setType(1);
        notif.setContent("收到新的请假申请");
        notif.setIsRead(0);
        notif.setCreatedAt(LocalDateTime.now());

        assertEquals(1L, notif.getId());
        assertEquals(1, notif.getType());
        assertEquals("收到新的请假申请", notif.getContent());
        assertEquals(0, notif.getIsRead());
    }

    @Test
    void testNotificationReadStatus() {
        Notification notif = new Notification();

        notif.setIsRead(0);
        assertEquals(0, notif.getIsRead());

        notif.setIsRead(1);
        assertEquals(1, notif.getIsRead());
    }
}
