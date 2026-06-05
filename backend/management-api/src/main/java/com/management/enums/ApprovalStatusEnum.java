package com.management.enums;

/**
 * 审批状态枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum ApprovalStatusEnum {

    /**
     * 待审批
     */
    PENDING(0, "待审批"),

    /**
     * 已通过
     */
    APPROVED(1, "已通过"),

    /**
     * 已驳回
     */
    REJECTED(2, "已驳回");

    private final int code;
    private final String desc;

    ApprovalStatusEnum(int code, String desc) {
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
     * @return ApprovalStatusEnum
     */
    public static ApprovalStatusEnum fromCode(int code) {
        for (ApprovalStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的审批状态编码: " + code);
    }
}
