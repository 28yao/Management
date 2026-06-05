import request from '@/utils/request'

/**
 * 认证相关 API
 */

/**
 * 登录
 * @param data 登录参数
 */
export function login(data: { account: string; password: string }) {
  return request.post('/auth/login', data)
}

/**
 * 登出
 */
export function logout() {
  return request.post('/auth/logout')
}

/**
 * 获取当前用户信息
 */
export function getUserInfo() {
  return request.get('/auth/me')
}

/**
 * 修改密码
 * @param data 密码参数
 */
export function changePassword(data: { oldPassword: string; newPassword: string }) {
  return request.put('/auth/password', data)
}
