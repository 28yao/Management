package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * 员工请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class EmployeeRequest {

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    @Size(max = 20, message = "工号不能超过20个字符")
    private String empNo;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名不能超过50个字符")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 入职日期
     */
    @NotNull(message = "入职日期不能为空")
    private LocalDate hireDate;

    /**
     * 职位
     */
    @NotBlank(message = "职位不能为空")
    @Size(max = 50, message = "职位不能超过50个字符")
    private String position;

    /**
     * 所属部门 ID
     */
    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空")
    @Size(max = 50, message = "登录账号不能超过50个字符")
    private String account;

    /**
     * 登录密码（新增时必填）
     */
    private String password;
}
