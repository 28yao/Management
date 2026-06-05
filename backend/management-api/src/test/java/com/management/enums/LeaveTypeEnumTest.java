package com.management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LeaveTypeEnum 测试
 *
 * @author management
 * @date 2024-01-01
 */
class LeaveTypeEnumTest {

    @Test
    void testLeaveTypeValues() {
        assertEquals(1, LeaveTypeEnum.PERSONAL.getCode());
        assertEquals(2, LeaveTypeEnum.SICK.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(LeaveTypeEnum.PERSONAL, LeaveTypeEnum.fromCode(1));
        assertEquals(LeaveTypeEnum.SICK, LeaveTypeEnum.fromCode(2));
    }
}
