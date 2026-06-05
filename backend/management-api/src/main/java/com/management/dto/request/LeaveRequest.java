package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 请假请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class LeaveRequest {

    /**
     * 假期类型：1-事假，2-病假
     */
    @NotNull(message = "假期类型不能为空")
    @Min(value = 1, message = "假期类型不正确")
    private Integer type;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    /**
     * 请假事由
     */
    @NotBlank(message = "请假事由不能为空")
    private String reason;
}
