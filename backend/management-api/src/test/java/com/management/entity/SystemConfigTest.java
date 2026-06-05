package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SystemConfig 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class SystemConfigTest {

    @Test
    void testCreateSystemConfig() {
        SystemConfig config = new SystemConfig();
        config.setId(1L);
        config.setConfigKey("work_start_time");
        config.setConfigValue("09:00");
        config.setDescription("上班时间");
        config.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, config.getId());
        assertEquals("work_start_time", config.getConfigKey());
        assertEquals("09:00", config.getConfigValue());
        assertEquals("上班时间", config.getDescription());
    }
}
