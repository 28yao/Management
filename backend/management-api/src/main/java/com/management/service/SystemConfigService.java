package com.management.service;

import java.util.Map;

/**
 * 系统配置服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface SystemConfigService {

    /**
     * 获取考勤配置
     *
     * @return 配置 Map
     */
    Map<String, String> getAttendanceConfig();

    /**
     * 更新考勤配置
     *
     * @param config 配置 Map
     */
    void updateAttendanceConfig(Map<String, String> config);
}
