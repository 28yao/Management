package com.management.service;

import com.management.entity.Department;

import java.util.List;

/**
 * 部门服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface DepartmentService {

    /**
     * 获取部门列表
     *
     * @return 部门列表
     */
    List<Department> listAll();

    /**
     * 新增部门
     *
     * @param department 部门信息
     * @return 部门
     */
    Department create(Department department);

    /**
     * 修改部门
     *
     * @param id         部门 ID
     * @param department 部门信息
     * @return 部门
     */
    Department update(Long id, Department department);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     */
    void delete(Long id);

    /**
     * 根据 ID 获取部门
     *
     * @param id 部门 ID
     * @return 部门
     */
    Department getById(Long id);
}
