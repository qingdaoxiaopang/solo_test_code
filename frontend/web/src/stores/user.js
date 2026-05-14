import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as apiLogin, logout as apiLogout, getUserInfo as apiGetUserInfo } from '@/api/auth'
import storage from '@/utils/storage'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref(storage.get('token') || '')
  const userInfo = ref(null)
  const permissions = ref([])
  const menus = ref([])

  const isLoggedIn = computed(() => !!token.value)
  const userId = computed(() => userInfo.value?.id)
  const username = computed(() => userInfo.value?.username || '')
  const nickname = computed(() => userInfo.value?.nickname || userInfo.value?.username || '')
  const avatar = computed(() => userInfo.value?.avatar || '')
  const roles = computed(() => userInfo.value?.roles || [])

  const hasPermission = (permission) => {
    if (!permission) return true
    if (Array.isArray(permission)) {
      return permission.some(p => permissions.value.includes(p))
    }
    return permissions.value.includes(permission)
  }

  const hasRole = (role) => {
    if (!role) return true
    if (Array.isArray(role)) {
      return role.some(r => roles.value.includes(r))
    }
    return roles.value.includes(role)
  }

  const login = async (loginForm) => {
    try {
      const res = await apiLogin(loginForm)
      token.value = res.data?.token || res.token
      storage.set('token', token.value)
      await getUserInfo()
      return res
    } catch (error) {
      throw error
    }
  }

  const logout = async () => {
    try {
      await apiLogout()
    } catch (error) {
      console.error('Logout error:', error)
    } finally {
      resetToken()
      router.push('/login')
    }
  }

  const getUserInfo = async () => {
    try {
      const res = await apiGetUserInfo()
      userInfo.value = res.data || res
      permissions.value = res.data?.permissions || []
      menus.value = res.data?.menus || []
      storage.set('userInfo', userInfo.value)
      return userInfo.value
    } catch (error) {
      throw error
    }
  }

  const resetToken = () => {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    menus.value = []
    storage.remove('token')
    storage.remove('userInfo')
  }

  const initUser = async () => {
    if (token.value && !userInfo.value) {
      try {
        await getUserInfo()
      } catch (error) {
        resetToken()
        router.push('/login')
      }
    }
  }

  return {
    token,
    userInfo,
    permissions,
    menus,
    isLoggedIn,
    userId,
    username,
    nickname,
    avatar,
    roles,
    hasPermission,
    hasRole,
    login,
    logout,
    getUserInfo,
    resetToken,
    initUser
  }
})
