package com.management.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Position 实体测试
 *
 * @author management
 * @date 2024-01-01
 */
class PositionTest {

    @Test
    void testCreatePosition() {
        Position pos = new Position();
        pos.setId(1L);
        pos.setName("部门经理");
        pos.setCreatedAt(LocalDateTime.now());
        pos.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, pos.getId());
        assertEquals("部门经理", pos.getName());
        assertNotNull(pos.getCreatedAt());
    }
}
