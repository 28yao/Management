package com.management.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * 考勤配置请求 DTO
 *
 * @author management
 * @date 2024-01-01
 */
@Data
public class AttendanceConfigRequest {

    /**
     * 上班时间
     */
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "时间格式不正确，应为 HH:mm")
    private String workStartTime;

    /**
     * 下班时间
     */
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "时间格式不正确，应为 HH:mm")
    private String workEndTime;
}
