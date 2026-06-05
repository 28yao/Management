package com.management.service;

import com.management.entity.Position;
import com.management.exception.BusinessException;
import com.management.mapper.EmployeeMapper;
import com.management.mapper.PositionMapper;
import com.management.service.impl.PositionServiceImpl;
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
 * PositionService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    private PositionMapper positionMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private PositionServiceImpl positionService;

    private Position testPosition;

    @BeforeEach
    void setUp() {
        testPosition = new Position();
        testPosition.setId(1L);
        testPosition.setName("部门经理");
    }

    @Test
    void testListAll() {
        when(positionMapper.selectList(null)).thenReturn(Arrays.asList(testPosition));

        List<Position> result = positionService.listAll();

        assertEquals(1, result.size());
        assertEquals("部门经理", result.get(0).getName());
    }

    @Test
    void testCreatePosition() {
        when(positionMapper.selectCount(any())).thenReturn(0L);
        when(positionMapper.insert(any())).thenReturn(1);

        Position result = positionService.create(testPosition);

        assertNotNull(result);
    }

    @Test
    void testCreateDuplicatePosition() {
        when(positionMapper.selectCount(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> positionService.create(testPosition));
    }

    @Test
    void testDeletePositionWithEmployees() {
        when(positionMapper.selectById(1L)).thenReturn(testPosition);
        when(employeeMapper.selectCount(any())).thenReturn(3L);

        assertThrows(BusinessException.class, () -> positionService.delete(1L));
    }

    @Test
    void testDeletePositionWithoutEmployees() {
        when(positionMapper.selectById(1L)).thenReturn(testPosition);
        when(employeeMapper.selectCount(any())).thenReturn(0L);
        when(positionMapper.deleteById(1L)).thenReturn(1);

        assertDoesNotThrow(() -> positionService.delete(1L));
    }
}
