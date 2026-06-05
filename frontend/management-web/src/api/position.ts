import request from '@/utils/request'

/**
 * 职位管理 API
 */

/**
 * 获取职位列表
 */
export function getPositionList() {
  return request.get('/positions')
}

/**
 * 新增职位
 * @param data 职位数据
 */
export function createPosition(data: { name: string }) {
  return request.post('/positions', data)
}

/**
 * 修改职位
 * @param id 职位 ID
 * @param data 职位数据
 */
export function updatePosition(id: number, data: { name: string }) {
  return request.put(`/positions/${id}`, data)
}

/**
 * 删除职位
 * @param id 职位 ID
 */
export function deletePosition(id: number) {
  return request.delete(`/positions/${id}`)
}
