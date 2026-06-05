package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.entity.Employee;
import com.management.exception.BusinessException;
import com.management.mapper.EmployeeMapper;
import com.management.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 员工服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public IPage<Employee> listPage(Page<Employee> page, Long deptId, String keyword) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        if (deptId != null) {
            wrapper.eq(Employee::getDeptId, deptId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w
                    .like(Employee::getName, keyword)
                    .or()
                    .like(Employee::getEmpNo, keyword)
                    .or()
                    .like(Employee::getAccount, keyword)
            );
        }
        wrapper.orderByDesc(Employee::getCreatedAt);

        return employeeMapper.selectPage(page, wrapper);
    }

    @Override
    public Employee getById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public Employee create(Employee employee) {
        // 自动生成工号
        employee.setEmpNo(generateEmpNo());

        // 检查账号是否重复
        Long count = employeeMapper.selectCount(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getAccount, employee.getAccount())
        );
        if (count > 0) {
            throw new BusinessException("登录账号已存在");
        }

        // 加密密码
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setStatus(1);
        employeeMapper.insert(employee);
        return employee;
    }

    /**
     * 自动生成工号
     * 格式：EMP + 6位序号（如 EMP000001）
     *
     * @return 工号
     */
    private String generateEmpNo() {
        // 获取当前最大工号
        Long count = employeeMapper.selectCount(null);
        return String.format("EMP%06d", count + 1);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee existing = employeeMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("员工不存在");
        }

        // 工号不可修改
        if (!existing.getEmpNo().equals(employee.getEmpNo())) {
            throw new BusinessException("工号不可修改");
        }

        employee.setId(id);
        employee.setPassword(null); // 不更新密码
        employeeMapper.updateById(employee);
        return employeeMapper.selectById(id);
    }

    @Override
    public void resign(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        employee.setStatus(0);
        employeeMapper.updateById(employee);
    }

    @Override
    public String resetPassword(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }

        String newPassword = "123456";
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeMapper.updateById(employee);
        return newPassword;
    }

    @Override
    public long countByDeptId(Long deptId) {
        return employeeMapper.selectCount(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getDeptId, deptId)
        );
    }
}
