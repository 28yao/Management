package com.management.service;

import com.management.entity.Notification;
import com.management.mapper.NotificationMapper;
import com.management.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * NotificationService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setEmpId(1L);
        testNotification.setType(1);
        testNotification.setContent("收到新的请假申请");
        testNotification.setIsRead(0);
    }

    @Test
    void testSendNotification() {
        when(notificationMapper.insert(any())).thenReturn(1);

        assertDoesNotThrow(() -> notificationService.send(testNotification));
        verify(notificationMapper).insert(any());
    }

    @Test
    void testGetUnreadCount() {
        when(notificationMapper.selectCount(any())).thenReturn(5L);

        long count = notificationService.getUnreadCount(1L);

        assertEquals(5L, count);
    }

    @Test
    void testMarkAsRead() {
        when(notificationMapper.selectById(1L)).thenReturn(testNotification);
        when(notificationMapper.updateById(any())).thenReturn(1);

        notificationService.markAsRead(1L);

        assertEquals(1, testNotification.getIsRead());
    }
}
