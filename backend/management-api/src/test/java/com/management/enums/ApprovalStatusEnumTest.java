package com.management.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ApprovalStatusEnum 测试
 *
 * @author management
 * @date 2024-01-01
 */
class ApprovalStatusEnumTest {

    @Test
    void testApprovalStatusValues() {
        assertEquals(0, ApprovalStatusEnum.PENDING.getCode());
        assertEquals(1, ApprovalStatusEnum.APPROVED.getCode());
        assertEquals(2, ApprovalStatusEnum.REJECTED.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(ApprovalStatusEnum.PENDING, ApprovalStatusEnum.fromCode(0));
        assertEquals(ApprovalStatusEnum.APPROVED, ApprovalStatusEnum.fromCode(1));
        assertEquals(ApprovalStatusEnum.REJECTED, ApprovalStatusEnum.fromCode(2));
    }
}
