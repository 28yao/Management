import request from '@/utils/request'

/**
 * 员工管理 API
 */

/**
 * 获取员工列表
 * @param params 查询参数
 */
export function getEmployeeList(params?: Record<string, any>) {
  return request.get('/employees', { params })
}

/**
 * 获取员工详情
 * @param id 员工 ID
 */
export function getEmployeeDetail(id: number) {
  return request.get(`/employees/${id}`)
}

/**
 * 新增员工
 * @param data 员工数据
 */
export function createEmployee(data: Record<string, any>) {
  return request.post('/employees', data)
}

/**
 * 修改员工信息
 * @param id 员工 ID
 * @param data 员工数据
 */
export function updateEmployee(id: number, data: Record<string, any>) {
  return request.put(`/employees/${id}`, data)
}

/**
 * 标记员工离职
 * @param id 员工 ID
 */
export function resignEmployee(id: number) {
  return request.put(`/employees/${id}/resign`)
}

/**
 * 重置员工密码
 * @param id 员工 ID
 */
export function resetPassword(id: number) {
  return request.put(`/employees/${id}/reset-password`)
}
