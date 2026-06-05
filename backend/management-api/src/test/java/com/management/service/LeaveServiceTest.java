package com.management.service;

import com.management.entity.LeaveRecord;
import com.management.exception.BusinessException;
import com.management.mapper.LeaveRecordMapper;
import com.management.service.impl.LeaveServiceImpl;
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
 * LeaveService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRecordMapper leaveRecordMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private LeaveRecord testLeave;

    @BeforeEach
    void setUp() {
        testLeave = new LeaveRecord();
        testLeave.setId(1L);
        testLeave.setEmpId(1L);
        testLeave.setType(1);
        testLeave.setStartDate(LocalDate.now());
        testLeave.setEndDate(LocalDate.now().plusDays(2));
        testLeave.setReason("个人事务");
        testLeave.setStatus(0);
    }

    @Test
    void testSubmitLeave() {
        when(leaveRecordMapper.selectCount(any())).thenReturn(0L);
        when(leaveRecordMapper.insert(any())).thenReturn(1);

        LeaveRecord result = leaveService.submit(testLeave);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
    }

    @Test
    void testSubmitLeaveWithOverlap() {
        when(leaveRecordMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> leaveService.submit(testLeave));
    }

    @Test
    void testSubmitLeaveWithInvalidDate() {
        testLeave.setEndDate(LocalDate.now().minusDays(1));

        assertThrows(BusinessException.class, () -> leaveService.submit(testLeave));
    }

    @Test
    void testApproveLeave() {
        when(leaveRecordMapper.selectById(1L)).thenReturn(testLeave);
        when(leaveRecordMapper.updateById(any())).thenReturn(1);

        leaveService.approve(1L, 1L);

        assertEquals(1, testLeave.getStatus());
        assertEquals(1L, testLeave.getApproverId());
    }

    @Test
    void testRejectLeave() {
        when(leaveRecordMapper.selectById(1L)).thenReturn(testLeave);
        when(leaveRecordMapper.updateById(any())).thenReturn(1);

        leaveService.reject(1L, 1L, "理由不充分");

        assertEquals(2, testLeave.getStatus());
        assertEquals("理由不充分", testLeave.getRejectReason());
    }

    @Test
    void testApproveAlreadyProcessed() {
        testLeave.setStatus(1);
        when(leaveRecordMapper.selectById(1L)).thenReturn(testLeave);

        assertThrows(BusinessException.class, () -> leaveService.approve(1L, 1L));
    }
}
