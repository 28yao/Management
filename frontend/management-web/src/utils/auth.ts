const TOKEN_KEY = 'management_token'
const USER_KEY = 'management_user'

/**
 * Token 管理工具
 * 处理 JWT Token 的存储、读取和清除
 */

/**
 * 获取 Token
 * @returns Token 字符串或 null
 */
export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}

/**
 * 设置 Token
 * @param token Token 字符串
 */
export function setToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token)
}

/**
 * 清除 Token
 */
export function removeToken(): void {
  localStorage.removeItem(TOKEN_KEY)
}

/**
 * 获取用户信息
 * @returns 用户信息对象或 null
 */
export function getUserInfo(): Record<string, any> | null {
  const userStr = localStorage.getItem(USER_KEY)
  if (userStr) {
    try {
      return JSON.parse(userStr)
    } catch {
      return null
    }
  }
  return null
}

/**
 * 设置用户信息
 * @param user 用户信息对象
 */
export function setUserInfo(user: Record<string, any>): void {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

/**
 * 清除用户信息
 */
export function removeUserInfo(): void {
  localStorage.removeItem(USER_KEY)
}

/**
 * 清除所有认证信息
 */
export function clearAuth(): void {
  removeToken()
  removeUserInfo()
}

/**
 * 判断是否已登录
 * @returns 是否已登录
 */
export function isAuthenticated(): boolean {
  return !!getToken()
}
