package com.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 考勤记录实体类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@TableName("attendance")
public class Attendance {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工 ID
     */
    private Long empId;

    /**
     * 打卡日期
     */
    private LocalDate date;

    /**
     * 上班打卡时间
     */
    private LocalTime clockIn;

    /**
     * 下班打卡时间
     */
    private LocalTime clockOut;

    /**
     * 状态：0-缺勤，1-正常，2-迟到，3-早退，4-迟到且早退
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
