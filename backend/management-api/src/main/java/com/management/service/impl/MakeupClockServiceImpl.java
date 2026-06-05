package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Attendance;
import com.management.entity.MakeupClock;
import com.management.entity.Notification;
import com.management.enums.ApprovalStatusEnum;
import com.management.enums.NotificationTypeEnum;
import com.management.exception.BusinessException;
import com.management.mapper.MakeupClockMapper;
import com.management.service.AttendanceService;
import com.management.service.MakeupClockService;
import com.management.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 补卡服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class MakeupClockServiceImpl implements MakeupClockService {

    private final MakeupClockMapper makeupClockMapper;
    private final AttendanceService attendanceService;
    private final NotificationService notificationService;

    public MakeupClockServiceImpl(MakeupClockMapper makeupClockMapper,
                                  AttendanceService attendanceService,
                                  NotificationService notificationService) {
        this.makeupClockMapper = makeupClockMapper;
        this.attendanceService = attendanceService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public MakeupClock submit(MakeupClock makeupClock) {
        // 检查补卡日期是否在7天内
        if (makeupClock.getDate().isBefore(LocalDate.now().minusDays(7))) {
            throw new BusinessException("只能补7天内的记录");
        }

        // 检查该日期时段是否已有补卡申请
        Long count = makeupClockMapper.selectCount(
                new LambdaQueryWrapper<MakeupClock>()
                        .eq(MakeupClock::getEmpId, makeupClock.getEmpId())
                        .eq(MakeupClock::getDate, makeupClock.getDate())
                        .eq(MakeupClock::getPeriod, makeupClock.getPeriod())
                        .eq(MakeupClock::getStatus, ApprovalStatusEnum.PENDING.getCode())
        );
        if (count > 0) {
            throw new BusinessException("已有待审批的补卡申请");
        }

        makeupClock.setStatus(ApprovalStatusEnum.PENDING.getCode());
        makeupClockMapper.insert(makeupClock);

        // 发送通知给管理员
        Notification notification = new Notification();
        notification.setEmpId(1L);
        notification.setType(NotificationTypeEnum.MAKEUP_SUBMIT.getCode());
        notification.setContent("收到新的补卡申请");
        notification.setIsRead(0);
        notificationService.send(notification);

        return makeupClock;
    }

    @Override
    public IPage<MakeupClock> listMyMakeupClocks(Page<MakeupClock> page, Long empId) {
        return makeupClockMapper.selectPage(page,
                new LambdaQueryWrapper<MakeupClock>()
                        .eq(MakeupClock::getEmpId, empId)
                        .orderByDesc(MakeupClock::getCreatedAt)
        );
    }

    @Override
    public IPage<MakeupClock> listPending(Page<MakeupClock> page) {
        return makeupClockMapper.selectPage(page,
                new LambdaQueryWrapper<MakeupClock>()
                        .eq(MakeupClock::getStatus, ApprovalStatusEnum.PENDING.getCode())
                        .orderByDesc(MakeupClock::getCreatedAt)
        );
    }

    @Override
    public IPage<MakeupClock> listAll(Page<MakeupClock> page, Integer status) {
        LambdaQueryWrapper<MakeupClock> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(MakeupClock::getStatus, status);
        }
        wrapper.orderByDesc(MakeupClock::getCreatedAt);
        return makeupClockMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public void approve(Long id, Long approverId) {
        MakeupClock makeupClock = makeupClockMapper.selectById(id);
        if (makeupClock == null) {
            throw new BusinessException("补卡记录不存在");
        }
        if (makeupClock.getStatus() != ApprovalStatusEnum.PENDING.getCode()) {
            throw new BusinessException("该申请已处理");
        }

        makeupClock.setStatus(ApprovalStatusEnum.APPROVED.getCode());
        makeupClock.setApproverId(approverId);
        makeupClockMapper.updateById(makeupClock);

        // 更新考勤记录
        Attendance attendance = attendanceService.getByEmpIdAndDate(makeupClock.getEmpId(), makeupClock.getDate());
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setEmpId(makeupClock.getEmpId());
            attendance.setDate(makeupClock.getDate());
            attendance.setStatus(1); // 正常

            if (makeupClock.getPeriod() == 1) {
                attendance.setClockIn(LocalTime.of(9, 0));
            } else {
                attendance.setClockOut(LocalTime.of(18, 0));
            }
            attendanceService.updateStatus(attendance);
        } else {
            if (makeupClock.getPeriod() == 1) {
                attendance.setClockIn(LocalTime.of(9, 0));
            } else {
                attendance.setClockOut(LocalTime.of(18, 0));
            }
            attendanceService.updateStatus(attendance);
        }

        // 发送通知给员工
        Notification notification = new Notification();
        notification.setEmpId(makeupClock.getEmpId());
        notification.setType(NotificationTypeEnum.MAKEUP_APPROVE.getCode());
        notification.setContent("您的补卡申请已通过");
        notification.setIsRead(0);
        notificationService.send(notification);
    }

    @Override
    @Transactional
    public void reject(Long id, Long approverId, String rejectReason) {
        MakeupClock makeupClock = makeupClockMapper.selectById(id);
        if (makeupClock == null) {
            throw new BusinessException("补卡记录不存在");
        }
        if (makeupClock.getStatus() != ApprovalStatusEnum.PENDING.getCode()) {
            throw new BusinessException("该申请已处理");
        }

        makeupClock.setStatus(ApprovalStatusEnum.REJECTED.getCode());
        makeupClock.setApproverId(approverId);
        makeupClock.setRejectReason(rejectReason);
        makeupClockMapper.updateById(makeupClock);

        // 发送通知给员工
        Notification notification = new Notification();
        notification.setEmpId(makeupClock.getEmpId());
        notification.setType(NotificationTypeEnum.MAKEUP_REJECT.getCode());
        notification.setContent("您的补卡申请已驳回：" + rejectReason);
        notification.setIsRead(0);
        notificationService.send(notification);
    }

    @Override
    public MakeupClock getById(Long id) {
        return makeupClockMapper.selectById(id);
    }
}
