package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 补卡请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class MakeupClockRequest {

    /**
     * 补卡日期
     */
    @NotNull(message = "补卡日期不能为空")
    private LocalDate date;

    /**
     * 时段：1-上班，2-下班
     */
    @NotNull(message = "打卡时段不能为空")
    @Min(value = 1, message = "打卡时段不正确")
    private Integer period;

    /**
     * 补卡原因
     */
    @NotBlank(message = "补卡原因不能为空")
    private String reason;
}
