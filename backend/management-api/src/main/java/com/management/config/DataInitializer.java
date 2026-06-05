package com.management.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.entity.Employee;
import com.management.mapper.EmployeeMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 * 应用启动时初始化默认管理员账号
 *
 * @author management
 * @date 2024-01-01
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 检查管理员是否已存在
        Long count = employeeMapper.selectCount(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getAccount, "admin")
        );

        if (count == 0) {
            // 创建管理员账号
            Employee admin = new Employee();
            admin.setDeptId(1L);
            admin.setEmpNo("ADMIN001");
            admin.setName("系统管理员");
            admin.setPhone("13800138000");
            admin.setHireDate(java.time.LocalDate.of(2024, 1, 1));
            admin.setPosition("管理员");
            admin.setAccount("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(1);
            admin.setStatus(1);
            employeeMapper.insert(admin);
            System.out.println("默认管理员账号创建成功: admin / admin123");
        }
    }
}
