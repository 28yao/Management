import request from '@/utils/request'

/**
 * 系统配置 API
 */

/**
 * 获取考勤配置
 */
export function getAttendanceConfig() {
  return request.get('/config/attendance')
}

/**
 * 修改考勤配置
 * @param data 配置数据
 */
export function updateAttendanceConfig(data: Record<string, any>) {
  return request.put('/config/attendance', data)
}
