package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.dto.request.LoginRequest;
import com.management.dto.response.LoginResponse;
import com.management.entity.Employee;
import com.management.exception.BusinessException;
import com.management.mapper.EmployeeMapper;
import com.management.service.AuthService;
import com.management.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(EmployeeMapper employeeMapper,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 根据账号查询员工
        Employee employee = employeeMapper.selectOne(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getAccount, request.getAccount())
        );

        if (employee == null) {
            throw new BusinessException("账号或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }

        // 检查员工状态
        if (employee.getStatus() == 0) {
            throw new BusinessException("该账号已被禁用");
        }

        // 生成 Token
        String token = jwtUtil.generateToken(employee.getId(), employee.getAccount(), employee.getRole());

        // 构建用户信息
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", employee.getId());
        userInfo.put("account", employee.getAccount());
        userInfo.put("name", employee.getName());
        userInfo.put("role", employee.getRole());
        userInfo.put("empNo", employee.getEmpNo());
        userInfo.put("deptId", employee.getDeptId());

        return LoginResponse.builder()
                .token(token)
                .user(userInfo)
                .build();
    }

    @Override
    public Map<String, Object> getCurrentUser() {
        // TODO: 从 SecurityContext 获取当前用户
        return new HashMap<>();
    }

    @Override
    public void logout() {
        // JWT 无状态，客户端删除 Token 即可
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO: 实现修改密码逻辑
    }
}
