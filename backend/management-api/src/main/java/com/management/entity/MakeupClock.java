package com.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 补卡申请实体类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@TableName("makeup_clock")
public class MakeupClock {

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
     * 补卡日期
     */
    private LocalDate date;

    /**
     * 时段：1-上班，2-下班
     */
    private Integer period;

    /**
     * 补卡原因
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
