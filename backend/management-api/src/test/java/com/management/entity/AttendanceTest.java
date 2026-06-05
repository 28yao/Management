package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Attendance 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class AttendanceTest {

    @Test
    void testCreateAttendance() {
        Attendance att = new Attendance();
        att.setId(1L);
        att.setEmpId(1L);
        att.setDate(LocalDate.now());
        att.setClockIn(LocalTime.of(8, 55));
        att.setClockOut(LocalTime.of(18, 5));
        att.setStatus(1);
        att.setCreatedAt(LocalDateTime.now());
        att.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, att.getId());
        assertEquals(1L, att.getEmpId());
        assertNotNull(att.getDate());
        assertNotNull(att.getClockIn());
        assertNotNull(att.getClockOut());
        assertEquals(1, att.getStatus());
    }

    @Test
    void testAttendanceStatus() {
        Attendance att = new Attendance();

        att.setStatus(0);
        assertEquals(0, att.getStatus());

        att.setStatus(1);
        assertEquals(1, att.getStatus());

        att.setStatus(2);
        assertEquals(2, att.getStatus());
    }
}
