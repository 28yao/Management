package com.management.enums;

/**
 * 假期类型枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum LeaveTypeEnum {

    /**
     * 事假
     */
    PERSONAL(1, "事假"),

    /**
     * 病假
     */
    SICK(2, "病假");

    private final int code;
    private final String desc;

    LeaveTypeEnum(int code, String desc) {
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
     * @param code 假期类型编码
     * @return LeaveTypeEnum
     */
    public static LeaveTypeEnum fromCode(int code) {
        for (LeaveTypeEnum type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的假期类型编码: " + code);
    }
}
