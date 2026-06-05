import axios, { type AxiosInstance, type AxiosResponse, type InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, clearAuth } from './auth'
import router from '@/router'

/**
 * Axios 请求封装
 * 统一处理请求拦截、响应拦截和错误处理
 */

const service: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

/**
 * 请求拦截器
 * 自动注入 Token
 */
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = getToken()
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 * 统一处理响应数据和错误
 */
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const { code, message, data } = response.data

    if (code === 200) {
      return response.data
    }

    // 401 未认证 - 跳转登录
    if (code === 401) {
      clearAuth()
      router.push('/login')
      ElMessage.error('登录已过期，请重新登录')
      return Promise.reject(new Error(message))
    }

    // 其他错误
    ElMessage.error(message || '请求失败')
    return Promise.reject(new Error(message))
  },
  (error) => {
    if (error.response) {
      const { status } = error.response
      switch (status) {
        case 401:
          clearAuth()
          router.push('/login')
          ElMessage.error('登录已过期，请重新登录')
          break
        case 403:
          ElMessage.error('权限不足')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error('网络错误')
      }
    } else {
      ElMessage.error('网络连接失败')
    }
    return Promise.reject(error)
  }
)

export default service
