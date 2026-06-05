package com.management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RoleEnum 测试
 *
 * @author management
 * @date 2024-01-01
 */
class RoleEnumTest {

    @Test
    void testRoleValues() {
        assertEquals(0, RoleEnum.EMPLOYEE.getCode());
        assertEquals(1, RoleEnum.ADMIN.getCode());
    }

    @Test
    void testRoleDescriptions() {
        assertEquals("员工", RoleEnum.EMPLOYEE.getDesc());
        assertEquals("管理员", RoleEnum.ADMIN.getDesc());
    }

    @Test
    void testFromCode() {
        assertEquals(RoleEnum.EMPLOYEE, RoleEnum.fromCode(0));
        assertEquals(RoleEnum.ADMIN, RoleEnum.fromCode(1));
    }

    @Test
    void testFromCodeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromCode(99));
    }
}
