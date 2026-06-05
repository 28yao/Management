package com.management.service;

import com.management.entity.Employee;
import com.management.exception.BusinessException;
import com.management.mapper.EmployeeMapper;
import com.management.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * EmployeeService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setEmpNo("EMP000001");
        testEmployee.setName("张三");
        testEmployee.setAccount("zhangsan");
        testEmployee.setPassword("123456");
        testEmployee.setRole(0);
        testEmployee.setStatus(1);
    }

    @Test
    void testCreateEmployee() {
        when(employeeMapper.selectCount(any())).thenReturn(0L);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(employeeMapper.insert(any())).thenReturn(1);

        Employee result = employeeService.create(testEmployee);

        assertNotNull(result);
        assertEquals("EMP000001", result.getEmpNo());
        verify(employeeMapper).insert(any());
    }

    @Test
    void testCreateDuplicateAccount() {
        when(employeeMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> employeeService.create(testEmployee));
    }

    @Test
    void testResignEmployee() {
        when(employeeMapper.selectById(1L)).thenReturn(testEmployee);
        when(employeeMapper.updateById(any())).thenReturn(1);

        assertDoesNotThrow(() -> employeeService.resign(1L));
        assertEquals(0, testEmployee.getStatus());
    }

    @Test
    void testResignNonExistentEmployee() {
        when(employeeMapper.selectById(999L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> employeeService.resign(999L));
    }

    @Test
    void testResetPassword() {
        when(employeeMapper.selectById(1L)).thenReturn(testEmployee);
        when(passwordEncoder.encode(any())).thenReturn("encodedNewPassword");
        when(employeeMapper.updateById(any())).thenReturn(1);

        String newPassword = employeeService.resetPassword(1L);

        assertNotNull(newPassword);
        assertEquals(8, newPassword.length());
    }
}
