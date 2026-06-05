package com.management.controller;

import com.management.common.ApiResponse;
import com.management.dto.request.AttendanceConfigRequest;
import com.management.service.SystemConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 系统配置控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/config")
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    public SystemConfigController(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    /**
     * 获取考勤配置
     */
    @GetMapping("/attendance")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, String>> getAttendanceConfig() {
        return ApiResponse.success(systemConfigService.getAttendanceConfig());
    }

    /**
     * 修改考勤配置
     */
    @PutMapping("/attendance")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateAttendanceConfig(@Valid @RequestBody AttendanceConfigRequest request) {
        Map<String, String> config = new java.util.HashMap<>();
        if (request.getWorkStartTime() != null) {
            config.put("workStartTime", request.getWorkStartTime());
        }
        if (request.getWorkEndTime() != null) {
            config.put("workEndTime", request.getWorkEndTime());
        }
        systemConfigService.updateAttendanceConfig(config);
        return ApiResponse.success("配置更新成功", null);
    }
}
