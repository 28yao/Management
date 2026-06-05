package com.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Attendance;

import java.time.LocalDate;
import java.util.Map;

/**
 * 考勤服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface AttendanceService {

    /**
     * 上班打卡
     *
     * @param empId 员工 ID
     * @return 考勤记录
     */
    Attendance clockIn(Long empId);

    /**
     * 下班打卡
     *
     * @param empId 员工 ID
     * @return 考勤记录
     */
    Attendance clockOut(Long empId);

    /**
     * 获取员工指定日期的考勤记录
     *
     * @param empId 员工 ID
     * @param date  日期
     * @return 考勤记录
     */
    Attendance getByEmpIdAndDate(Long empId, LocalDate date);

    /**
     * 分页查询员工的考勤记录
     *
     * @param page   分页参数
     * @param empId  员工 ID
     * @param month  月份（可选）
     * @return 考勤分页
     */
    IPage<Attendance> listMyAttendance(Page<Attendance> page, Long empId, String month);

    /**
     * 分页查询所有考勤记录（管理员）
     *
     * @param page    分页参数
     * @param deptId  部门 ID（可选）
     * @param empId   员工 ID（可选）
     * @param month   月份（可选）
     * @return 考勤分页
     */
    IPage<Attendance> listAll(Page<Attendance> page, Long deptId, Long empId, String month);

    /**
     * 获取员工考勤统计
     *
     * @param empId 员工 ID
     * @param month 月份
     * @return 统计数据
     */
    Map<String, Object> getStatistics(Long empId, String month);

    /**
     * 更新考勤状态
     *
     * @param attendance 考勤记录
     */
    void updateStatus(Attendance attendance);
}
