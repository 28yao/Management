package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LeaveRecord 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class LeaveRecordTest {

    @Test
    void testCreateLeaveRecord() {
        LeaveRecord leave = new LeaveRecord();
        leave.setId(1L);
        leave.setEmpId(1L);
        leave.setType(1);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(2));
        leave.setReason("个人事务");
        leave.setStatus(0);
        leave.setCreatedAt(LocalDateTime.now());
        leave.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, leave.getId());
        assertEquals(1, leave.getType());
        assertEquals("个人事务", leave.getReason());
        assertEquals(0, leave.getStatus());
    }

    @Test
    void testLeaveApproval() {
        LeaveRecord leave = new LeaveRecord();
        leave.setStatus(0);
        assertEquals(0, leave.getStatus());

        leave.setStatus(1);
        leave.setApproverId(1L);
        assertEquals(1, leave.getStatus());
        assertEquals(1L, leave.getApproverId());
    }

    @Test
    void testLeaveRejection() {
        LeaveRecord leave = new LeaveRecord();
        leave.setStatus(2);
        leave.setApproverId(1L);
        leave.setRejectReason("理由不充分");

        assertEquals(2, leave.getStatus());
        assertEquals("理由不充分", leave.getRejectReason());
    }
}
