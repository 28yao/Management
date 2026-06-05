package com.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.entity.LeaveRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假记录 Mapper 接口
 *
 * @author management
 * @date 2024-01-01
 */
@Mapper
public interface LeaveRecordMapper extends BaseMapper<LeaveRecord> {
}
