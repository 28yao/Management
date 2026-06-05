package com.management.enums;

/**
 * 考勤状态枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum AttendanceStatusEnum {

    /**
     * 缺勤
     */
    ABSENT(0, "缺勤"),

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 迟到
     */
    LATE(2, "迟到"),

    /**
     * 早退
     */
    EARLY_LEAVE(3, "早退"),

    /**
     * 迟到且早退
     */
    LATE_AND_EARLY(4, "迟到且早退");

    private final int code;
    private final String desc;

    AttendanceStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据 code 获取枚举
     *
     * @param code 状态编码
     * @return AttendanceStatusEnum
     */
    public static AttendanceStatusEnum fromCode(int code) {
        for (AttendanceStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的考勤状态编码: " + code);
    }
}
