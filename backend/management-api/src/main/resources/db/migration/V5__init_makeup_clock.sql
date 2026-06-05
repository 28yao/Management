-- 创建补卡申请表
CREATE TABLE IF NOT EXISTS `makeup_clock` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `emp_id` BIGINT NOT NULL COMMENT '申请人ID',
    `date` DATE NOT NULL COMMENT '补卡日期',
    `period` TINYINT NOT NULL COMMENT '时段：1-上班，2-下班',
    `reason` VARCHAR(500) NOT NULL COMMENT '补卡原因',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待审批，1-已通过，2-已驳回',
    `approver_id` BIGINT DEFAULT NULL COMMENT '审批人ID',
    `reject_reason` VARCHAR(500) DEFAULT NULL COMMENT '驳回原因',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_emp_date_period` (`emp_id`, `date`, `period`),
    CONSTRAINT `fk_makeup_emp` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='补卡申请表';
