-- 创建考勤记录表
CREATE TABLE IF NOT EXISTS `attendance` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `emp_id` BIGINT NOT NULL COMMENT '员工ID',
    `date` DATE NOT NULL COMMENT '打卡日期',
    `clock_in` TIME DEFAULT NULL COMMENT '上班打卡时间',
    `clock_out` TIME DEFAULT NULL COMMENT '下班打卡时间',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-缺勤，1-正常，2-迟到，3-早退，4-迟到且早退',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_emp_date` (`emp_id`, `date`),
    CONSTRAINT `fk_att_emp` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤记录表';
