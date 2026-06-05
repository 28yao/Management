package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 职位请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class PositionRequest {

    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 50, message = "职位名称不能超过50个字符")
    private String name;
}
