import request from '@/utils/request'

/**
 * 通知管理 API
 */

/**
 * 获取我的通知列表
 * @param params 查询参数
 */
export function getMyNotifications(params?: Record<string, any>) {
  return request.get('/notifications/my', { params })
}

/**
 * 获取未读通知数量
 */
export function getUnreadCount() {
  return request.get('/notifications/my/unread-count')
}

/**
 * 标记通知已读
 * @param id 通知 ID
 */
export function markAsRead(id: number) {
  return request.put(`/notifications/${id}/read`)
}

/**
 * 标记所有通知已读
 */
export function markAllAsRead() {
  return request.put('/notifications/read-all')
}
