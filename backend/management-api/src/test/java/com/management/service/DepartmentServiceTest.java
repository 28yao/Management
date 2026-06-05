package com.management.service;

import com.management.entity.Department;
import com.management.exception.BusinessException;
import com.management.mapper.DepartmentMapper;
import com.management.mapper.EmployeeMapper;
import com.management.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * DepartmentService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department testDept;

    @BeforeEach
    void setUp() {
        testDept = new Department();
        testDept.setId(1L);
        testDept.setName("技术部");
    }

    @Test
    void testListAll() {
        when(departmentMapper.selectList(null)).thenReturn(Arrays.asList(testDept));

        List<Department> result = departmentService.listAll();

        assertEquals(1, result.size());
        assertEquals("技术部", result.get(0).getName());
    }

    @Test
    void testCreateDepartment() {
        when(departmentMapper.selectCount(any())).thenReturn(0L);
        when(departmentMapper.insert(any())).thenReturn(1);

        Department result = departmentService.create(testDept);

        assertNotNull(result);
        verify(departmentMapper).insert(any());
    }

    @Test
    void testCreateDuplicateDepartment() {
        when(departmentMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> departmentService.create(testDept));
    }

    @Test
    void testDeleteDepartmentWithEmployees() {
        when(departmentMapper.selectById(1L)).thenReturn(testDept);
        when(employeeMapper.selectCount(any())).thenReturn(5L);

        assertThrows(BusinessException.class, () -> departmentService.delete(1L));
    }

    @Test
    void testDeleteDepartmentWithoutEmployees() {
        when(departmentMapper.selectById(1L)).thenReturn(testDept);
        when(employeeMapper.selectCount(any())).thenReturn(0L);
        when(departmentMapper.deleteById(1L)).thenReturn(1);

        assertDoesNotThrow(() -> departmentService.delete(1L));
    }
}
