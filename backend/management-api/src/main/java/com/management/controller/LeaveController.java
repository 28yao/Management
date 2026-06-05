package com.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.common.ApiResponse;
import com.management.dto.request.LeaveRequest;
import com.management.dto.request.RejectRequest;
import com.management.entity.LeaveRecord;
import com.management.service.LeaveService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 请假管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * 提交请假申请
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<LeaveRecord> submit(Authentication authentication, @Valid @RequestBody LeaveRequest request) {
        Long empId = Long.valueOf(authentication.getName());
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setEmpId(empId);
        leaveRecord.setType(request.getType());
        leaveRecord.setStartDate(request.getStartDate());
        leaveRecord.setEndDate(request.getEndDate());
        leaveRecord.setReason(request.getReason());
        return ApiResponse.success(leaveService.submit(leaveRecord));
    }

    /**
     * 获取我的请假记录
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<IPage<LeaveRecord>> myLeaves(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Long empId = Long.valueOf(authentication.getName());
        Page<LeaveRecord> page = new Page<>(current, size);
        return ApiResponse.success(leaveService.listMyLeaves(page, empId));
    }

    /**
     * 获取待审批列表（管理员）
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<LeaveRecord>> pending(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<LeaveRecord> page = new Page<>(current, size);
        return ApiResponse.success(leaveService.listPending(page));
    }

    /**
     * 获取所有请假记录（管理员）
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<LeaveRecord>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<LeaveRecord> page = new Page<>(current, size);
        return ApiResponse.success(leaveService.listAll(page, status));
    }

    /**
     * 审批通过
     */
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> approve(Authentication authentication, @PathVariable Long id) {
        Long approverId = Long.valueOf(authentication.getName());
        leaveService.approve(id, approverId);
        return ApiResponse.success("审批通过", null);
    }

    /**
     * 审批驳回
     */
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> reject(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody RejectRequest request) {
        Long approverId = Long.valueOf(authentication.getName());
        leaveService.reject(id, approverId, request.getRejectReason());
        return ApiResponse.success("已驳回", null);
    }
}
