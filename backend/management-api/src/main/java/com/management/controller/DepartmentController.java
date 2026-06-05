package com.management.controller;

import com.management.common.ApiResponse;
import com.management.dto.request.DepartmentRequest;
import com.management.entity.Department;
import com.management.service.DepartmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 获取部门列表
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Department>> list() {
        return ApiResponse.success(departmentService.listAll());
    }

    /**
     * 新增部门
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Department> create(@Valid @RequestBody DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        return ApiResponse.success(departmentService.create(department));
    }

    /**
     * 修改部门
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Department> update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        return ApiResponse.success(departmentService.update(id, department));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ApiResponse.success("删除成功", null);
    }
}
