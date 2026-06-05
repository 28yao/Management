import request from '@/utils/request'

/**
 * 部门管理 API
 */

/**
 * 获取部门列表
 */
export function getDepartmentList() {
  return request.get('/departments')
}

/**
 * 新增部门
 * @param data 部门数据
 */
export function createDepartment(data: { name: string }) {
  return request.post('/departments', data)
}

/**
 * 修改部门
 * @param id 部门 ID
 * @param data 部门数据
 */
export function updateDepartment(id: number, data: { name: string }) {
  return request.put(`/departments/${id}`, data)
}

/**
 * 删除部门
 * @param id 部门 ID
 */
export function deleteDepartment(id: number) {
  return request.delete(`/departments/${id}`)
}
