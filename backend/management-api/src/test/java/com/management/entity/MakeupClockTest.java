package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MakeupClock 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class MakeupClockTest {

    @Test
    void testCreateMakeupClock() {
        MakeupClock mc = new MakeupClock();
        mc.setId(1L);
        mc.setEmpId(1L);
        mc.setDate(LocalDate.now().minusDays(1));
        mc.setPeriod(1);
        mc.setReason("忘记打卡");
        mc.setStatus(0);
        mc.setCreatedAt(LocalDateTime.now());
        mc.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, mc.getId());
        assertEquals(1, mc.getPeriod());
        assertEquals("忘记打卡", mc.getReason());
        assertEquals(0, mc.getStatus());
    }

    @Test
    void testMakeupClockPeriod() {
        MakeupClock mc = new MakeupClock();

        mc.setPeriod(1);
        assertEquals(1, mc.getPeriod());

        mc.setPeriod(2);
        assertEquals(2, mc.getPeriod());
    }
}
