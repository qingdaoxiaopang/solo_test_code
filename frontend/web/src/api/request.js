import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Toast } from 'vant'
import router from '@/router'
import storage from '@/utils/storage'

const BASE_URL = '/api'

const service = axios.create({
  baseURL: BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

service.interceptors.request.use(
  (config) => {
    const token = storage.get('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    const projectId = storage.get('currentProjectId')
    if (projectId) {
      config.headers['X-Project-Id'] = projectId
    }
    return config
  },
  (error) => {
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response) => {
    const res = response.data
    if (response.config.responseType === 'blob') {
      return res
    }
    if (res.code !== 200 && res.code !== 0) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  (error) => {
    console.error('Response error:', error)
    if (error.response) {
      const status = error.response.status
      const message = error.response.data?.message || '请求失败'
      switch (status) {
        case 401:
          ElMessageBox.confirm('登录已过期，请重新登录', '提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            storage.remove('token')
            router.push('/login')
          })
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 404:
          ElMessage.error('请求资源不存在')
          break
        case 500:
          ElMessage.error('服务器错误')
          break
        default:
          ElMessage.error(message)
      }
    } else if (error.code === 'ECONNABORTED') {
      ElMessage.error('请求超时')
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default service
