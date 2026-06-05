package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.entity.Department;
import com.management.exception.BusinessException;
import com.management.mapper.DepartmentMapper;
import com.management.mapper.EmployeeMapper;
import com.management.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper, EmployeeMapper employeeMapper) {
        this.departmentMapper = departmentMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.selectList(null);
    }

    @Override
    public Department create(Department department) {
        // 检查部门名称是否重复
        Long count = departmentMapper.selectCount(
                new LambdaQueryWrapper<Department>()
                        .eq(Department::getName, department.getName())
        );
        if (count > 0) {
            throw new BusinessException("部门名称已存在");
        }
        departmentMapper.insert(department);
        return department;
    }

    @Override
    public Department update(Long id, Department department) {
        Department existing = departmentMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查新名称是否与其他部门重复
        if (!existing.getName().equals(department.getName())) {
            Long count = departmentMapper.selectCount(
                    new LambdaQueryWrapper<Department>()
                            .eq(Department::getName, department.getName())
                            .ne(Department::getId, id)
            );
            if (count > 0) {
                throw new BusinessException("部门名称已存在");
            }
        }

        department.setId(id);
        departmentMapper.updateById(department);
        return departmentMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentMapper.selectById(id);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查部门下是否有员工
        Long empCount = employeeMapper.selectCount(
                new LambdaQueryWrapper<com.management.entity.Employee>()
                        .eq(com.management.entity.Employee::getDeptId, id)
        );
        if (empCount > 0) {
            throw new BusinessException("该部门下有 " + empCount + " 名员工，请先转移员工");
        }

        departmentMapper.deleteById(id);
    }

    @Override
    public Department getById(Long id) {
        return departmentMapper.selectById(id);
    }
}
