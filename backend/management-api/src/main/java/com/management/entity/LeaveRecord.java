package com.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 请假记录实体类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@TableName("leave_record")
public class LeaveRecord {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 申请人 ID
     */
    private Long empId;

    /**
     * 假期类型：1-事假，2-病假
     */
    private Integer type;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 请假事由
     */
    private String reason;

    /**
     * 状态：0-待审批，1-已通过，2-已驳回
     */
    private Integer status;

    /**
     * 审批人 ID
     */
    private Long approverId;

    /**
     * 驳回原因
     */
    private String rejectReason;

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
