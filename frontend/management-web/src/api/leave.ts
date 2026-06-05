import request from '@/utils/request'

/**
 * 请假管理 API
 */

/**
 * 提交请假申请
 * @param data 请假数据
 */
export function createLeave(data: Record<string, any>) {
  return request.post('/leaves', data)
}

/**
 * 获取我的请假记录
 * @param params 查询参数
 */
export function getMyLeaves(params?: Record<string, any>) {
  return request.get('/leaves/my', { params })
}

/**
 * 获取待审批列表（管理员）
 * @param params 查询参数
 */
export function getPendingLeaves(params?: Record<string, any>) {
  return request.get('/leaves/pending', { params })
}

/**
 * 获取所有请假记录（管理员）
 * @param params 查询参数
 */
export function getLeaveList(params?: Record<string, any>) {
  return request.get('/leaves', { params })
}

/**
 * 审批通过（管理员）
 * @param id 请假 ID
 */
export function approveLeave(id: number) {
  return request.put(`/leaves/${id}/approve`)
}

/**
 * 审批驳回（管理员）
 * @param id 请假 ID
 * @param data 驳回原因
 */
export function rejectLeave(id: number, data: { rejectReason: string }) {
  return request.put(`/leaves/${id}/reject`, data)
}
