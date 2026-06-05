-- 创建请假记录表
CREATE TABLE IF NOT EXISTS `leave_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `emp_id` BIGINT NOT NULL COMMENT '申请人ID',
    `type` TINYINT NOT NULL COMMENT '假期类型：1-事假，2-病假',
    `start_date` DATE NOT NULL COMMENT '开始日期',
    `end_date` DATE NOT NULL COMMENT '结束日期',
    `reason` VARCHAR(500) NOT NULL COMMENT '请假事由',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待审批，1-已通过，2-已驳回',
    `approver_id` BIGINT DEFAULT NULL COMMENT '审批人ID',
    `reject_reason` VARCHAR(500) DEFAULT NULL COMMENT '驳回原因',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_emp_id` (`emp_id`),
    KEY `idx_status` (`status`),
    CONSTRAINT `fk_leave_emp` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假记录表';
