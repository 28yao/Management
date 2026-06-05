package com.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 *
 * @author management
 * @date 2024-01-01
 */
@Data
@TableName("employee")
public class Employee {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属部门 ID
     */
    private Long deptId;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 入职日期
     */
    private LocalDate hireDate;

    /**
     * 职位
     */
    private String position;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 角色：0-员工，1-管理员
     */
    private Integer role;

    /**
     * 状态：0-离职，1-在职
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
