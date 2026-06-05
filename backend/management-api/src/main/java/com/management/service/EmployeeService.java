package com.management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Employee;

/**
 * 员工服务接口
 *
 * @author management
 * @date 2024-01-01
 */
public interface EmployeeService {

    /**
     * 分页查询员工列表
     *
     * @param page     分页参数
     * @param deptId   部门 ID（可选）
     * @param keyword  关键词（可选）
     * @return 员工分页
     */
    IPage<Employee> listPage(Page<Employee> page, Long deptId, String keyword);

    /**
     * 根据 ID 获取员工
     *
     * @param id 员工 ID
     * @return 员工
     */
    Employee getById(Long id);

    /**
     * 新增员工
     *
     * @param employee 员工信息
     * @return 员工
     */
    Employee create(Employee employee);

    /**
     * 修改员工
     *
     * @param id       员工 ID
     * @param employee 员工信息
     * @return 员工
     */
    Employee update(Long id, Employee employee);

    /**
     * 标记员工离职
     *
     * @param id 员工 ID
     */
    void resign(Long id);

    /**
     * 重置员工密码
     *
     * @param id 员工 ID
     * @return 新密码
     */
    String resetPassword(Long id);

    /**
     * 统计部门下的员工数量
     *
     * @param deptId 部门 ID
     * @return 员工数量
     */
    long countByDeptId(Long deptId);
}
