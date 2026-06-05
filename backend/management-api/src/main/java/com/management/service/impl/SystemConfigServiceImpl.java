package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.entity.SystemConfig;
import com.management.mapper.SystemConfigMapper;
import com.management.service.SystemConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    private final SystemConfigMapper systemConfigMapper;

    public SystemConfigServiceImpl(SystemConfigMapper systemConfigMapper) {
        this.systemConfigMapper = systemConfigMapper;
    }

    @Override
    public Map<String, String> getAttendanceConfig() {
        Map<String, String> config = new HashMap<>();

        SystemConfig startTime = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, "work_start_time")
        );
        SystemConfig endTime = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, "work_end_time")
        );

        config.put("workStartTime", startTime != null ? startTime.getConfigValue() : "09:00");
        config.put("workEndTime", endTime != null ? endTime.getConfigValue() : "18:00");

        return config;
    }

    @Override
    public void updateAttendanceConfig(Map<String, String> config) {
        if (config.containsKey("workStartTime")) {
            updateConfig("work_start_time", config.get("workStartTime"), "上班时间");
        }
        if (config.containsKey("workEndTime")) {
            updateConfig("work_end_time", config.get("workEndTime"), "下班时间");
        }
    }

    private void updateConfig(String key, String value, String description) {
        SystemConfig existing = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, key)
        );
        if (existing != null) {
            existing.setConfigValue(value);
            systemConfigMapper.updateById(existing);
        } else {
            SystemConfig newConfig = new SystemConfig();
            newConfig.setConfigKey(key);
            newConfig.setConfigValue(value);
            newConfig.setDescription(description);
            systemConfigMapper.insert(newConfig);
        }
    }
}
