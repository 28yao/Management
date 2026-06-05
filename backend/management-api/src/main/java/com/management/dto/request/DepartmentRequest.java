package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 部门请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class DepartmentRequest {

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称不能超过50个字符")
    private String name;
}
