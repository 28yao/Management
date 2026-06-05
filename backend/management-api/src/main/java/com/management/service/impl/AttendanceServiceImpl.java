package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Attendance;
import com.management.entity.SystemConfig;
import com.management.enums.AttendanceStatusEnum;
import com.management.exception.BusinessException;
import com.management.mapper.AttendanceMapper;
import com.management.mapper.SystemConfigMapper;
import com.management.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 考勤服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceMapper attendanceMapper;
    private final SystemConfigMapper systemConfigMapper;

    public AttendanceServiceImpl(AttendanceMapper attendanceMapper, SystemConfigMapper systemConfigMapper) {
        this.attendanceMapper = attendanceMapper;
        this.systemConfigMapper = systemConfigMapper;
    }

    @Override
    public Attendance clockIn(Long empId) {
        LocalDate today = LocalDate.now();

        // 检查今日是否已打卡
        Attendance existing = getByEmpIdAndDate(empId, today);
        if (existing != null && existing.getClockIn() != null) {
            throw new BusinessException("今日已打上班卡");
        }

        LocalTime now = LocalTime.now();
        LocalTime workStart = getWorkStartTime();

        // 判断是否迟到
        int status = now.isAfter(workStart)
                ? AttendanceStatusEnum.LATE.getCode()
                : AttendanceStatusEnum.NORMAL.getCode();

        if (existing == null) {
            existing = new Attendance();
            existing.setEmpId(empId);
            existing.setDate(today);
            existing.setClockIn(now);
            existing.setStatus(status);
            attendanceMapper.insert(existing);
        } else {
            existing.setClockIn(now);
            existing.setStatus(status);
            attendanceMapper.updateById(existing);
        }

        return existing;
    }

    @Override
    public Attendance clockOut(Long empId) {
        LocalDate today = LocalDate.now();

        // 检查是否已打上班卡
        Attendance existing = getByEmpIdAndDate(empId, today);
        if (existing == null || existing.getClockIn() == null) {
            throw new BusinessException("请先打上班卡");
        }

        if (existing.getClockOut() != null) {
            throw new BusinessException("今日已打下班卡");
        }

        LocalTime now = LocalTime.now();
        LocalTime workEnd = getWorkEndTime();

        // 判断是否早退
        if (now.isBefore(workEnd)) {
            existing.setStatus(existing.getStatus() == AttendanceStatusEnum.LATE.getCode()
                    ? AttendanceStatusEnum.LATE_AND_EARLY.getCode()
                    : AttendanceStatusEnum.EARLY_LEAVE.getCode());
        }

        existing.setClockOut(now);
        attendanceMapper.updateById(existing);

        return existing;
    }

    @Override
    public Attendance getByEmpIdAndDate(Long empId, LocalDate date) {
        return attendanceMapper.selectOne(
                new LambdaQueryWrapper<Attendance>()
                        .eq(Attendance::getEmpId, empId)
                        .eq(Attendance::getDate, date)
        );
    }

    @Override
    public IPage<Attendance> listMyAttendance(Page<Attendance> page, Long empId, String month) {
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attendance::getEmpId, empId);

        if (month != null && !month.isEmpty()) {
            wrapper.apply("DATE_FORMAT(date, '%Y-%m') = {0}", month);
        }
        wrapper.orderByDesc(Attendance::getDate);

        return attendanceMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<Attendance> listAll(Page<Attendance> page, Long deptId, Long empId, String month) {
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();

        if (empId != null) {
            wrapper.eq(Attendance::getEmpId, empId);
        }
        if (month != null && !month.isEmpty()) {
            wrapper.apply("DATE_FORMAT(date, '%Y-%m') = {0}", month);
        }
        wrapper.orderByDesc(Attendance::getDate);

        return attendanceMapper.selectPage(page, wrapper);
    }

    @Override
    public Map<String, Object> getStatistics(Long empId, String month) {
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attendance::getEmpId, empId);
        if (month != null && !month.isEmpty()) {
            wrapper.apply("DATE_FORMAT(date, '%Y-%m') = {0}", month);
        }

        java.util.List<Attendance> records = attendanceMapper.selectList(wrapper);

        long totalDays = records.size();
        long normalDays = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatusEnum.NORMAL.getCode())
                .count();
        long lateDays = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatusEnum.LATE.getCode()
                        || r.getStatus() == AttendanceStatusEnum.LATE_AND_EARLY.getCode())
                .count();
        long earlyDays = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatusEnum.EARLY_LEAVE.getCode()
                        || r.getStatus() == AttendanceStatusEnum.LATE_AND_EARLY.getCode())
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDays", totalDays);
        stats.put("normalDays", normalDays);
        stats.put("lateDays", lateDays);
        stats.put("earlyDays", earlyDays);
        return stats;
    }

    @Override
    public void updateStatus(Attendance attendance) {
        attendanceMapper.updateById(attendance);
    }

    private LocalTime getWorkStartTime() {
        SystemConfig config = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, "work_start_time")
        );
        return LocalTime.parse(config != null ? config.getConfigValue() : "09:00");
    }

    private LocalTime getWorkEndTime() {
        SystemConfig config = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, "work_end_time")
        );
        return LocalTime.parse(config != null ? config.getConfigValue() : "18:00");
    }
}
