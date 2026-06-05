package com.management.service;

import com.management.entity.Position;

import java.util.List;

/**
 * 职位服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface PositionService {

    /**
     * 获取职位列表
     *
     * @return 职位列表
     */
    List<Position> listAll();

    /**
     * 新增职位
     *
     * @param position 职位信息
     * @return 职位
     */
    Position create(Position position);

    /**
     * 修改职位
     *
     * @param id       职位 ID
     * @param position 职位信息
     * @return 职位
     */
    Position update(Long id, Position position);

    /**
     * 删除职位
     *
     * @param id 职位 ID
     */
    void delete(Long id);
}
