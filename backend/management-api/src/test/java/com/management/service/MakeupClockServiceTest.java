package com.management.service;

import com.management.entity.MakeupClock;
import com.management.exception.BusinessException;
import com.management.mapper.MakeupClockMapper;
import com.management.service.impl.MakeupClockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * MakeupClockService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class MakeupClockServiceTest {

    @Mock
    private MakeupClockMapper makeupClockMapper;

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MakeupClockServiceImpl makeupClockService;

    private MakeupClock testMakeupClock;

    @BeforeEach
    void setUp() {
        testMakeupClock = new MakeupClock();
        testMakeupClock.setId(1L);
        testMakeupClock.setEmpId(1L);
        testMakeupClock.setDate(LocalDate.now().minusDays(1));
        testMakeupClock.setPeriod(1);
        testMakeupClock.setReason("忘记打卡");
        testMakeupClock.setStatus(0);
    }

    @Test
    void testSubmitMakeupClock() {
        when(makeupClockMapper.selectCount(any())).thenReturn(0L);
        when(makeupClockMapper.insert(any())).thenReturn(1);

        MakeupClock result = makeupClockService.submit(testMakeupClock);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
    }

    @Test
    void testSubmitMakeupClockExceeds7Days() {
        testMakeupClock.setDate(LocalDate.now().minusDays(10));

        assertThrows(BusinessException.class, () -> makeupClockService.submit(testMakeupClock));
    }

    @Test
    void testSubmitDuplicateMakeupClock() {
        when(makeupClockMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> makeupClockService.submit(testMakeupClock));
    }

    @Test
    void testApproveMakeupClock() {
        when(makeupClockMapper.selectById(1L)).thenReturn(testMakeupClock);
        when(makeupClockMapper.updateById(any())).thenReturn(1);

        makeupClockService.approve(1L, 1L);

        assertEquals(1, testMakeupClock.getStatus());
    }

    @Test
    void testRejectMakeupClock() {
        when(makeupClockMapper.selectById(1L)).thenReturn(testMakeupClock);
        when(makeupClockMapper.updateById(any())).thenReturn(1);

        makeupClockService.reject(1L, 1L, "不符合补卡条件");

        assertEquals(2, testMakeupClock.getStatus());
    }
}
