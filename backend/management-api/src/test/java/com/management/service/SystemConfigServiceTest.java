package com.management.service;

import com.management.entity.SystemConfig;
import com.management.mapper.SystemConfigMapper;
import com.management.service.impl.SystemConfigServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SystemConfigService 测试
 *
 * @author management
 * @date 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class SystemConfigServiceTest {

    @Mock
    private SystemConfigMapper systemConfigMapper;

    @InjectMocks
    private SystemConfigServiceImpl systemConfigService;

    @Test
    void testGetAttendanceConfig() {
        SystemConfig startTime = new SystemConfig();
        startTime.setConfigKey("work_start_time");
        startTime.setConfigValue("09:00");

        SystemConfig endTime = new SystemConfig();
        endTime.setConfigKey("work_end_time");
        endTime.setConfigValue("18:00");

        when(systemConfigMapper.selectOne(any()))
                .thenReturn(startTime)
                .thenReturn(endTime);

        Map<String, String> config = systemConfigService.getAttendanceConfig();

        assertEquals("09:00", config.get("workStartTime"));
        assertEquals("18:00", config.get("workEndTime"));
    }

    @Test
    void testUpdateAttendanceConfig() {
        SystemConfig existing = new SystemConfig();
        existing.setId(1L);
        existing.setConfigKey("work_start_time");
        existing.setConfigValue("09:00");

        when(systemConfigMapper.selectOne(any())).thenReturn(existing);
        when(systemConfigMapper.updateById(any())).thenReturn(1);

        Map<String, String> config = new HashMap<>();
        config.put("workStartTime", "08:30");

        assertDoesNotThrow(() -> systemConfigService.updateAttendanceConfig(config));
    }
}
