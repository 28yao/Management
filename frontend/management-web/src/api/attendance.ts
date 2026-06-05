import request from '@/utils/request'

/**
 * 考勤管理 API
 */

/**
 * 上班打卡
 */
export function clockIn() {
  return request.post('/attendance/clock-in')
}

/**
 * 下班打卡
 */
export function clockOut() {
  return request.post('/attendance/clock-out')
}

/**
 * 获取个人考勤记录
 * @param params 查询参数
 */
export function getMyAttendance(params?: Record<string, any>) {
  return request.get('/attendance/my', { params })
}

/**
 * 获取个人考勤统计
 * @param params 查询参数
 */
export function getMyStatistics(params?: Record<string, any>) {
  return request.get('/attendance/my/statistics', { params })
}

/**
 * 获取所有考勤记录（管理员）
 * @param params 查询参数
 */
export function getAttendanceList(params?: Record<string, any>) {
  return request.get('/attendance', { params })
}

/**
 * 获取考勤统计（管理员）
 * @param params 查询参数
 */
export function getAttendanceStatistics(params?: Record<string, any>) {
  return request.get('/attendance/statistics', { params })
}
