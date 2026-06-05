package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 驳回请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class RejectRequest {

    /**
     * 驳回原因
     */
    @NotBlank(message = "驳回原因不能为空")
    private String rejectReason;
}
