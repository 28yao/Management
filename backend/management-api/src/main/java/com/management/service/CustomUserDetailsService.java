package com.management.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.entity.Employee;
import com.management.mapper.EmployeeMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 自定义 UserDetailsService
 * 从数据库加载用户信息
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeMapper employeeMapper;

    public CustomUserDetailsService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Employee employee = employeeMapper.selectOne(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getAccount, account)
        );

        if (employee == null) {
            throw new UsernameNotFoundException("账号不存在: " + account);
        }

        if (employee.getStatus() == 0) {
            throw new UsernameNotFoundException("账号已被禁用: " + account);
        }

        String roleName = employee.getRole() == 1 ? "ROLE_ADMIN" : "ROLE_EMPLOYEE";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        return new User(
                String.valueOf(employee.getId()),
                employee.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
