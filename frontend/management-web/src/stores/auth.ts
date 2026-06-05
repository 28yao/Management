import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getToken, setToken, removeToken, getUserInfo, setUserInfo, clearAuth } from '@/utils/auth'

/**
 * 认证状态 Store
 * 管理用户登录状态、Token 和用户信息
 */
export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(getToken())
  const userInfo = ref<Record<string, any> | null>(getUserInfo())

  /**
   * 是否已登录
   */
  const isLoggedIn = computed(() => !!token.value)

  /**
   * 用户角色
   */
  const role = computed(() => userInfo.value?.role)

  /**
   * 是否是管理员
   */
  const isAdmin = computed(() => role.value === 1)

  /**
   * 设置认证信息
   * @param tokenStr Token 字符串
   * @param user 用户信息
   */
  function setAuth(tokenStr: string, user: Record<string, any>) {
    token.value = tokenStr
    userInfo.value = user
    setToken(tokenStr)
    setUserInfo(user)
  }

  /**
   * 登出
   */
  function logout() {
    token.value = null
    userInfo.value = null
    clearAuth()
  }

  /**
   * 获取用户名
   */
  const username = computed(() => userInfo.value?.name || userInfo.value?.account || '')

  return {
    token,
    userInfo,
    isLoggedIn,
    role,
    isAdmin,
    username,
    setAuth,
    logout
  }
})
