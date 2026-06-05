package com.management.enums;

/**
 * 通知类型枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum NotificationTypeEnum {

    /**
     * 请假申请
     */
    LEAVE_SUBMIT(1, "请假申请"),

    /**
     * 请假通过
     */
    LEAVE_APPROVE(2, "请假通过"),

    /**
     * 请假驳回
     */
    LEAVE_REJECT(3, "请假驳回"),

    /**
     * 补卡申请
     */
    MAKEUP_SUBMIT(4, "补卡申请"),

    /**
     * 补卡通过
     */
    MAKEUP_APPROVE(5, "补卡通过"),

    /**
     * 补卡驳回
     */
    MAKEUP_REJECT(6, "补卡驳回");

    private final int code;
    private final String desc;

    NotificationTypeEnum(int code, String desc) {
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
     * @param code 类型编码
     * @return NotificationTypeEnum
     */
    public static NotificationTypeEnum fromCode(int code) {
        for (NotificationTypeEnum type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的通知类型编码: " + code);
    }
}
