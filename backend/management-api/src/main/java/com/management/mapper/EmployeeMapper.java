package com.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工 Mapper 接口
 *
 * @author management
 * @date 2024-01-01
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
