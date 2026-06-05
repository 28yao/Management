package com.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.common.ApiResponse;
import com.management.entity.Attendance;
import com.management.service.AttendanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 考勤管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * 上班打卡
     */
    @PostMapping("/clock-in")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Attendance> clockIn(Authentication authentication) {
        Long empId = Long.valueOf(authentication.getName());
        return ApiResponse.success(attendanceService.clockIn(empId));
    }

    /**
     * 下班打卡
     */
    @PostMapping("/clock-out")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Attendance> clockOut(Authentication authentication) {
        Long empId = Long.valueOf(authentication.getName());
        return ApiResponse.success(attendanceService.clockOut(empId));
    }

    /**
     * 获取个人考勤记录
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<IPage<Attendance>> myAttendance(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String month) {
        Long empId = Long.valueOf(authentication.getName());
        Page<Attendance> page = new Page<>(current, size);
        return ApiResponse.success(attendanceService.listMyAttendance(page, empId, month));
    }

    /**
     * 获取个人考勤统计
     */
    @GetMapping("/my/statistics")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Map<String, Object>> myStatistics(
            Authentication authentication,
            @RequestParam(required = false) String month) {
        Long empId = Long.valueOf(authentication.getName());
        return ApiResponse.success(attendanceService.getStatistics(empId, month));
    }

    /**
     * 获取所有考勤记录（管理员）
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<Attendance>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) Long empId,
            @RequestParam(required = false) String month) {
        Page<Attendance> page = new Page<>(current, size);
        return ApiResponse.success(attendanceService.listAll(page, deptId, empId, month));
    }
}
