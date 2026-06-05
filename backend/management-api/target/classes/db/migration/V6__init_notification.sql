-- 创建通知表
CREATE TABLE IF NOT EXISTS `notification` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `emp_id` BIGINT NOT NULL COMMENT '接收人ID',
    `type` TINYINT NOT NULL COMMENT '通知类型：1-请假申请，2-请假通过，3-请假驳回，4-补卡申请，5-补卡通过，6-补卡驳回',
    `content` VARCHAR(500) NOT NULL COMMENT '通知内容',
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_emp_id` (`emp_id`),
    KEY `idx_is_read` (`is_read`),
    CONSTRAINT `fk_notif_emp` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';
