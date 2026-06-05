-- 创建员工表
CREATE TABLE IF NOT EXISTS `employee` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `dept_id` BIGINT NOT NULL COMMENT '所属部门ID',
    `emp_no` VARCHAR(20) NOT NULL COMMENT '工号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `hire_date` DATE NOT NULL COMMENT '入职日期',
    `position` VARCHAR(50) NOT NULL COMMENT '职位',
    `account` VARCHAR(50) NOT NULL COMMENT '登录账号',
    `password` VARCHAR(100) NOT NULL COMMENT '登录密码',
    `role` TINYINT NOT NULL DEFAULT 0 COMMENT '角色：0-员工，1-管理员',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-离职，1-在职',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_emp_no` (`emp_no`),
    UNIQUE KEY `uk_account` (`account`),
    KEY `idx_dept_id` (`dept_id`),
    CONSTRAINT `fk_emp_dept` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';
