package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Employee 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class EmployeeTest {

    @Test
    void testCreateEmployee() {
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setDeptId(1L);
        emp.setEmpNo("EMP000001");
        emp.setName("张三");
        emp.setPhone("13800138000");
        emp.setEmail("zhangsan@example.com");
        emp.setHireDate(LocalDate.of(2024, 1, 1));
        emp.setPosition("员工");
        emp.setAccount("zhangsan");
        emp.setPassword("encodedPassword");
        emp.setRole(0);
        emp.setStatus(1);
        emp.setCreatedAt(LocalDateTime.now());
        emp.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, emp.getId());
        assertEquals("EMP000001", emp.getEmpNo());
        assertEquals("张三", emp.getName());
        assertEquals(0, emp.getRole());
        assertEquals(1, emp.getStatus());
    }

    @Test
    void testEmployeeRole() {
        Employee emp = new Employee();

        emp.setRole(0);
        assertEquals(0, emp.getRole());

        emp.setRole(1);
        assertEquals(1, emp.getRole());
    }

    @Test
    void testEmployeeStatus() {
        Employee emp = new Employee();

        emp.setStatus(1);
        assertEquals(1, emp.getStatus());

        emp.setStatus(0);
        assertEquals(0, emp.getStatus());
    }
}
