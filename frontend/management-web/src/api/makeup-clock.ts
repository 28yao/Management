import request from '@/utils/request'

/**
 * 补卡管理 API
 */

/**
 * 提交补卡申请
 * @param data 补卡数据
 */
export function createMakeupClock(data: Record<string, any>) {
  return request.post('/makeup-clocks', data)
}

/**
 * 获取我的补卡记录
 * @param params 查询参数
 */
export function getMyMakeupClocks(params?: Record<string, any>) {
  return request.get('/makeup-clocks/my', { params })
}

/**
 * 获取待审批列表（管理员）
 * @param params 查询参数
 */
export function getPendingMakeupClocks(params?: Record<string, any>) {
  return request.get('/makeup-clocks/pending', { params })
}

/**
 * 获取所有补卡记录（管理员）
 * @param params 查询参数
 */
export function getMakeupClockList(params?: Record<string, any>) {
  return request.get('/makeup-clocks', { params })
}

/**
 * 审批通过（管理员）
 * @param id 补卡 ID
 */
export function approveMakeupClock(id: number) {
  return request.put(`/makeup-clocks/${id}/approve`)
}

/**
 * 审批驳回（管理员）
 * @param id 补卡 ID
 * @param data 驳回原因
 */
export function rejectMakeupClock(id: number, data: { rejectReason: string }) {
  return request.put(`/makeup-clocks/${id}/reject`, data)
}
