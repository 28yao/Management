package com.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.common.ApiResponse;
import com.management.dto.request.MakeupClockRequest;
import com.management.dto.request.RejectRequest;
import com.management.entity.MakeupClock;
import com.management.service.MakeupClockService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 补卡管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/makeup-clocks")
public class MakeupClockController {

    private final MakeupClockService makeupClockService;

    public MakeupClockController(MakeupClockService makeupClockService) {
        this.makeupClockService = makeupClockService;
    }

    /**
     * 提交补卡申请
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<MakeupClock> submit(Authentication authentication, @Valid @RequestBody MakeupClockRequest request) {
        Long empId = Long.valueOf(authentication.getName());
        MakeupClock makeupClock = new MakeupClock();
        makeupClock.setEmpId(empId);
        makeupClock.setDate(request.getDate());
        makeupClock.setPeriod(request.getPeriod());
        makeupClock.setReason(request.getReason());
        return ApiResponse.success(makeupClockService.submit(makeupClock));
    }

    /**
     * 获取我的补卡记录
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<IPage<MakeupClock>> myMakeupClocks(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Long empId = Long.valueOf(authentication.getName());
        Page<MakeupClock> page = new Page<>(current, size);
        return ApiResponse.success(makeupClockService.listMyMakeupClocks(page, empId));
    }

    /**
     * 获取待审批列表（管理员）
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<MakeupClock>> pending(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<MakeupClock> page = new Page<>(current, size);
        return ApiResponse.success(makeupClockService.listPending(page));
    }

    /**
     * 获取所有补卡记录（管理员）
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<MakeupClock>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<MakeupClock> page = new Page<>(current, size);
        return ApiResponse.success(makeupClockService.listAll(page, status));
    }

    /**
     * 审批通过
     */
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> approve(Authentication authentication, @PathVariable Long id) {
        Long approverId = Long.valueOf(authentication.getName());
        makeupClockService.approve(id, approverId);
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
        makeupClockService.reject(id, approverId, request.getRejectReason());
        return ApiResponse.success("已驳回", null);
    }
}
