package com.management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.common.ApiResponse;
import com.management.dto.request.EmployeeRequest;
import com.management.entity.Employee;
import com.management.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 员工管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 获取员工列表（分页）
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<IPage<Employee>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) String keyword) {
        Page<Employee> page = new Page<>(current, size);
        return ApiResponse.success(employeeService.listPage(page, deptId, keyword));
    }

    /**
     * 获取员工详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Employee> detail(@PathVariable Long id) {
        return ApiResponse.success(employeeService.getById(id));
    }

    /**
     * 新增员工
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Employee> create(@Valid @RequestBody EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmpNo(request.getEmpNo());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setHireDate(request.getHireDate());
        employee.setPosition(request.getPosition());
        employee.setDeptId(request.getDeptId());
        employee.setAccount(request.getAccount());
        employee.setPassword(request.getPassword());
        employee.setRole(0);
        return ApiResponse.success(employeeService.create(employee));
    }

    /**
     * 修改员工信息
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmpNo(request.getEmpNo());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setHireDate(request.getHireDate());
        employee.setPosition(request.getPosition());
        employee.setDeptId(request.getDeptId());
        employee.setAccount(request.getAccount());
        return ApiResponse.success(employeeService.update(id, employee));
    }

    /**
     * 标记员工离职
     */
    @PutMapping("/{id}/resign")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> resign(@PathVariable Long id) {
        employeeService.resign(id);
        return ApiResponse.success("离职处理成功", null);
    }

    /**
     * 重置员工密码
     */
    @PutMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> resetPassword(@PathVariable Long id) {
        String newPassword = employeeService.resetPassword(id);
        return ApiResponse.success("重置成功，新密码：" + newPassword, null);
    }
}
