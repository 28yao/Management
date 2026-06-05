package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Department 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class DepartmentTest {

    @Test
    void testCreateDepartment() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("技术部");
        dept.setCreatedAt(LocalDateTime.now());
        dept.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, dept.getId());
        assertEquals("技术部", dept.getName());
        assertNotNull(dept.getCreatedAt());
        assertNotNull(dept.getUpdatedAt());
    }

    @Test
    void testDepartmentEquality() {
        Department dept1 = new Department();
        dept1.setId(1L);
        dept1.setName("技术部");

        Department dept2 = new Department();
        dept2.setId(1L);
        dept2.setName("技术部");

        assertEquals(dept1.getId(), dept2.getId());
        assertEquals(dept1.getName(), dept2.getName());
    }
}
