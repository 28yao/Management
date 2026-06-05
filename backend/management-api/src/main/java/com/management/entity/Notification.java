package com.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知实体类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@TableName("notification")
public class Notification {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接收人 ID
     */
    private Long empId;

    /**
     * 通知类型：1-请假申请，2-请假通过，3-请假驳回，4-补卡申请，5-补卡通过，6-补卡驳回
     */
    private Integer type;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
