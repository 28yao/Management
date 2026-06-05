package com.management.service;

import com.management.entity.Attendance;
import com.management.entity.SystemConfig;
import com.management.exception.BusinessException;
import com.management.mapper.AttendanceMapper;
import com.management.mapper.SystemConfigMapper;
import com.management.service.impl.AttendanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AttendanceService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private SystemConfigMapper systemConfigMapper;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private SystemConfig startTimeConfig;
    private SystemConfig endTimeConfig;

    @BeforeEach
    void setUp() {
        startTimeConfig = new SystemConfig();
        startTimeConfig.setConfigKey("work_start_time");
        startTimeConfig.setConfigValue("09:00");

        endTimeConfig = new SystemConfig();
        endTimeConfig.setConfigKey("work_end_time");
        endTimeConfig.setConfigValue("18:00");
    }

    @Test
    void testClockInFirstTime() {
        when(attendanceMapper.selectOne(any())).thenReturn(null);
        when(systemConfigMapper.selectOne(any())).thenReturn(startTimeConfig);
        when(attendanceMapper.insert(any())).thenReturn(1);

        Attendance result = attendanceService.clockIn(1L);

        assertNotNull(result);
        verify(attendanceMapper).insert(any());
    }

    @Test
    void testClockInAlreadyDone() {
        Attendance existing = new Attendance();
        existing.setClockIn(LocalTime.now());
        when(attendanceMapper.selectOne(any())).thenReturn(existing);

        assertThrows(BusinessException.class, () -> attendanceService.clockIn(1L));
    }

    @Test
    void testClockOutWithoutClockIn() {
        when(attendanceMapper.selectOne(any())).thenReturn(null);

        assertThrows(BusinessException.class, () -> attendanceService.clockOut(1L));
    }

    @Test
    void testClockOutSuccess() {
        Attendance existing = new Attendance();
        existing.setClockIn(LocalTime.of(9, 0));
        existing.setClockOut(null);
        existing.setStatus(1);

        when(attendanceMapper.selectOne(any())).thenReturn(existing);
        when(systemConfigMapper.selectOne(any())).thenReturn(endTimeConfig);
        when(attendanceMapper.updateById(any())).thenReturn(1);

        Attendance result = attendanceService.clockOut(1L);

        assertNotNull(result);
        assertNotNull(result.getClockOut());
    }
}
