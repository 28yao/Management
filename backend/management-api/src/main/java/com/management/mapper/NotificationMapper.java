package com.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知 Mapper 接口
 *
 * @author management
 * @date 2024-01-01
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
