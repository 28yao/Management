package com.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.MakeupClock;

/**
 * 补卡服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface MakeupClockService {

    /**
     * 提交补卡申请
     *
     * @param makeupClock 补卡记录
     * @return 补卡记录
     */
    MakeupClock submit(MakeupClock makeupClock);

    /**
     * 获取员工的补卡记录
     *
     * @param page  分页参数
     * @param empId 员工 ID
     * @return 补卡分页
     */
    IPage<MakeupClock> listMyMakeupClocks(Page<MakeupClock> page, Long empId);

    /**
     * 获取待审批列表（管理员）
     *
     * @param page 分页参数
     * @return 补卡分页
     */
    IPage<MakeupClock> listPending(Page<MakeupClock> page);

    /**
     * 获取所有补卡记录（管理员）
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 补卡分页
     */
    IPage<MakeupClock> listAll(Page<MakeupClock> page, Integer status);

    /**
     * 审批通过
     *
     * @param id         补卡 ID
     * @param approverId 审批人 ID
     */
    void approve(Long id, Long approverId);

    /**
     * 审批驳回
     *
     * @param id           补卡 ID
     * @param approverId   审批人 ID
     * @param rejectReason 驳回原因
     */
    void reject(Long id, Long approverId, String rejectReason);

    /**
     * 根据 ID 获取补卡记录
     *
     * @param id 补卡 ID
     * @return 补卡记录
     */
    MakeupClock getById(Long id);
}
