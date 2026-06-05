package com.management.enums;

/**
 * 角色枚举
 *
 * @author management
 * @date 2024-01-01
 */
public enum RoleEnum {

    /**
     * 普通员工
     */
    EMPLOYEE(0, "员工"),

    /**
     * 管理员
     */
    ADMIN(1, "管理员");

    private final int code;
    private final String desc;

    RoleEnum(int code, String desc) {
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
     * @param code 角色编码
     * @return RoleEnum
     */
    public static RoleEnum fromCode(int code) {
        for (RoleEnum role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("未知的角色编码: " + code);
    }
}
