package com.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.LeaveRecord;

/**
 * 请假服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface LeaveService {

    /**
     * 提交请假申请
     *
     * @param leaveRecord 请假记录
     * @return 请假记录
     */
    LeaveRecord submit(LeaveRecord leaveRecord);

    /**
     * 获取员工的请假记录
     *
     * @param page  分页参数
     * @param empId 员工 ID
     * @return 请假分页
     */
    IPage<LeaveRecord> listMyLeaves(Page<LeaveRecord> page, Long empId);

    /**
     * 获取待审批列表（管理员）
     *
     * @param page 分页参数
     * @return 请假分页
     */
    IPage<LeaveRecord> listPending(Page<LeaveRecord> page);

    /**
     * 获取所有请假记录（管理员）
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 请假分页
     */
    IPage<LeaveRecord> listAll(Page<LeaveRecord> page, Integer status);

    /**
     * 审批通过
     *
     * @param id        请假 ID
     * @param approverId 审批人 ID
     */
    void approve(Long id, Long approverId);

    /**
     * 审批驳回
     *
     * @param id            请假 ID
     * @param approverId    审批人 ID
     * @param rejectReason  驳回原因
     */
    void reject(Long id, Long approverId, String rejectReason);

    /**
     * 根据 ID 获取请假记录
     *
     * @param id 请假 ID
     * @return 请假记录
     */
    LeaveRecord getById(Long id);
}
