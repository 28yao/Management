package com.management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AttendanceStatusEnum 测试
 *
 * @author management
 * @date 2024-01-01
 */
class AttendanceStatusEnumTest {

    @Test
    void testStatusValues() {
        assertEquals(0, AttendanceStatusEnum.ABSENT.getCode());
        assertEquals(1, AttendanceStatusEnum.NORMAL.getCode());
        assertEquals(2, AttendanceStatusEnum.LATE.getCode());
        assertEquals(3, AttendanceStatusEnum.EARLY_LEAVE.getCode());
        assertEquals(4, AttendanceStatusEnum.LATE_AND_EARLY.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(AttendanceStatusEnum.NORMAL, AttendanceStatusEnum.fromCode(1));
        assertEquals(AttendanceStatusEnum.LATE, AttendanceStatusEnum.fromCode(2));
    }
}
