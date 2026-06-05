package com.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门 Mapper 接口
 *
 * @author management
 * @date 2024-01-01
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}
