package com.management.enums;

/**
 * 打卡时段枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum ClockPeriodEnum {

    /**
     * 上班
     */
    CLOCK_IN(1, "上班"),

    /**
     * 下班
     */
    CLOCK_OUT(2, "下班");

    private final int code;
    private final String desc;

    ClockPeriodEnum(int code, String desc) {
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
     * @param code 时段编码
     * @return ClockPeriodEnum
     */
    public static ClockPeriodEnum fromCode(int code) {
        for (ClockPeriodEnum period : values()) {
            if (period.code == code) {
                return period;
            }
        }
        throw new IllegalArgumentException("未知的打卡时段编码: " + code);
    }
}
