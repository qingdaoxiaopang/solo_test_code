import storage from './storage'
import router from '@/router'

export const checkAuth = () => {
  const token = storage.getToken()
  if (!token) {
    router.push('/login')
    return false
  }
  return true
}

export const getToken = () => {
  return storage.getToken()
}

export const setToken = (token) => {
  storage.setToken(token)
}

export const removeToken = () => {
  storage.removeToken()
}

export const hasPermission = (permissions, requiredPermissions) => {
  if (!requiredPermissions || requiredPermissions.length === 0) {
    return true
  }
  if (!permissions || permissions.length === 0) {
    return false
  }
  if (Array.isArray(requiredPermissions)) {
    return requiredPermissions.some(p => permissions.includes(p))
  }
  return permissions.includes(requiredPermissions)
}

export const hasRole = (roles, requiredRoles) => {
  if (!requiredRoles || requiredRoles.length === 0) {
    return true
  }
  if (!roles || roles.length === 0) {
    return false
  }
  if (Array.isArray(requiredRoles)) {
    return requiredRoles.some(r => roles.includes(r))
  }
  return roles.includes(requiredRoles)
}
