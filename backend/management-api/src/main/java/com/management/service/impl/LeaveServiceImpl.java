package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.LeaveRecord;
import com.management.entity.Notification;
import com.management.enums.ApprovalStatusEnum;
import com.management.enums.NotificationTypeEnum;
import com.management.exception.BusinessException;
import com.management.mapper.LeaveRecordMapper;
import com.management.service.LeaveService;
import com.management.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 请假服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRecordMapper leaveRecordMapper;
    private final NotificationService notificationService;

    public LeaveServiceImpl(LeaveRecordMapper leaveRecordMapper, NotificationService notificationService) {
        this.leaveRecordMapper = leaveRecordMapper;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public LeaveRecord submit(LeaveRecord leaveRecord) {
        // 验证日期
        if (leaveRecord.getEndDate().isBefore(leaveRecord.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }

        // 检查是否有时间重叠的请假
        Long count = leaveRecordMapper.selectCount(
                new LambdaQueryWrapper<LeaveRecord>()
                        .eq(LeaveRecord::getEmpId, leaveRecord.getEmpId())
                        .eq(LeaveRecord::getStatus, ApprovalStatusEnum.PENDING.getCode())
                        .le(LeaveRecord::getStartDate, leaveRecord.getEndDate())
                        .ge(LeaveRecord::getEndDate, leaveRecord.getStartDate())
        );
        if (count > 0) {
            throw new BusinessException("该时间段已有待审批的请假申请");
        }

        leaveRecord.setStatus(ApprovalStatusEnum.PENDING.getCode());
        leaveRecordMapper.insert(leaveRecord);

        // 发送通知给管理员
        Notification notification = new Notification();
        notification.setEmpId(1L); // 管理员 ID
        notification.setType(NotificationTypeEnum.LEAVE_SUBMIT.getCode());
        notification.setContent("收到新的请假申请");
        notification.setIsRead(0);
        notificationService.send(notification);

        return leaveRecord;
    }

    @Override
    public IPage<LeaveRecord> listMyLeaves(Page<LeaveRecord> page, Long empId) {
        return leaveRecordMapper.selectPage(page,
                new LambdaQueryWrapper<LeaveRecord>()
                        .eq(LeaveRecord::getEmpId, empId)
                        .orderByDesc(LeaveRecord::getCreatedAt)
        );
    }

    @Override
    public IPage<LeaveRecord> listPending(Page<LeaveRecord> page) {
        return leaveRecordMapper.selectPage(page,
                new LambdaQueryWrapper<LeaveRecord>()
                        .eq(LeaveRecord::getStatus, ApprovalStatusEnum.PENDING.getCode())
                        .orderByDesc(LeaveRecord::getCreatedAt)
        );
    }

    @Override
    public IPage<LeaveRecord> listAll(Page<LeaveRecord> page, Integer status) {
        LambdaQueryWrapper<LeaveRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(LeaveRecord::getStatus, status);
        }
        wrapper.orderByDesc(LeaveRecord::getCreatedAt);
        return leaveRecordMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public void approve(Long id, Long approverId) {
        LeaveRecord leave = leaveRecordMapper.selectById(id);
        if (leave == null) {
            throw new BusinessException("请假记录不存在");
        }
        if (leave.getStatus() != ApprovalStatusEnum.PENDING.getCode()) {
            throw new BusinessException("该申请已处理");
        }

        leave.setStatus(ApprovalStatusEnum.APPROVED.getCode());
        leave.setApproverId(approverId);
        leaveRecordMapper.updateById(leave);

        // 发送通知给员工
        Notification notification = new Notification();
        notification.setEmpId(leave.getEmpId());
        notification.setType(NotificationTypeEnum.LEAVE_APPROVE.getCode());
        notification.setContent("您的请假申请已通过");
        notification.setIsRead(0);
        notificationService.send(notification);
    }

    @Override
    @Transactional
    public void reject(Long id, Long approverId, String rejectReason) {
        LeaveRecord leave = leaveRecordMapper.selectById(id);
        if (leave == null) {
            throw new BusinessException("请假记录不存在");
        }
        if (leave.getStatus() != ApprovalStatusEnum.PENDING.getCode()) {
            throw new BusinessException("该申请已处理");
        }

        leave.setStatus(ApprovalStatusEnum.REJECTED.getCode());
        leave.setApproverId(approverId);
        leave.setRejectReason(rejectReason);
        leaveRecordMapper.updateById(leave);

        // 发送通知给员工
        Notification notification = new Notification();
        notification.setEmpId(leave.getEmpId());
        notification.setType(NotificationTypeEnum.LEAVE_REJECT.getCode());
        notification.setContent("您的请假申请已驳回：" + rejectReason);
        notification.setIsRead(0);
        notificationService.send(notification);
    }

    @Override
    public LeaveRecord getById(Long id) {
        return leaveRecordMapper.selectById(id);
    }
}
