import request from './request'

export const login = (data) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export const logout = () => {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export const getUserInfo = () => {
  return request({
    url: '/auth/userinfo',
    method: 'get'
  })
}

export const refreshToken = () => {
  return request({
    url: '/auth/refresh',
    method: 'post'
  })
}

export const updatePassword = (data) => {
  return request({
    url: '/auth/password',
    method: 'put',
    data
  })
}

export const getMenus = () => {
  return request({
    url: '/auth/menus',
    method: 'get'
  })
}
