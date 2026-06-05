-- 创建系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key` VARCHAR(50) NOT NULL COMMENT '配置键',
    `config_value` VARCHAR(100) NOT NULL COMMENT '配置值',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '配置说明',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 插入默认考勤配置
INSERT INTO `system_config` (`config_key`, `config_value`, `description`) VALUES
('work_start_time', '09:00', '上班时间'),
('work_end_time', '18:00', '下班时间');
