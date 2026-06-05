package com.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.management.entity.Employee;
import com.management.entity.Position;
import com.management.exception.BusinessException;
import com.management.mapper.EmployeeMapper;
import com.management.mapper.PositionMapper;
import com.management.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位服务实现类
 *
 * @author management
 * @date 2024-01-01
 */
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionMapper positionMapper;
    private final EmployeeMapper employeeMapper;

    public PositionServiceImpl(PositionMapper positionMapper, EmployeeMapper employeeMapper) {
        this.positionMapper = positionMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Position> listAll() {
        return positionMapper.selectList(null);
    }

    @Override
    public Position create(Position position) {
        // 检查职位名称是否重复
        Long count = positionMapper.selectCount(
                new LambdaQueryWrapper<Position>()
                        .eq(Position::getName, position.getName())
        );
        if (count > 0) {
            throw new BusinessException("职位名称已存在");
        }
        positionMapper.insert(position);
        return position;
    }

    @Override
    public Position update(Long id, Position position) {
        Position existing = positionMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("职位不存在");
        }

        // 检查新名称是否与其他职位重复
        if (!existing.getName().equals(position.getName())) {
            Long count = positionMapper.selectCount(
                    new LambdaQueryWrapper<Position>()
                            .eq(Position::getName, position.getName())
                            .ne(Position::getId, id)
            );
            if (count > 0) {
                throw new BusinessException("职位名称已存在");
            }
        }

        position.setId(id);
        positionMapper.updateById(position);
        return positionMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        Position position = positionMapper.selectById(id);
        if (position == null) {
            throw new BusinessException("职位不存在");
        }

        // 检查是否有员工使用该职位
        Long empCount = employeeMapper.selectCount(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getPosition, position.getName())
        );
        if (empCount > 0) {
            throw new BusinessException("该职位下有 " + empCount + " 名员工，请先修改员工职位");
        }

        positionMapper.deleteById(id);
    }
}
