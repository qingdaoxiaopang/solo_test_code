import request from './request'

export const getUserList = (params) => {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

export const getUserById = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'get'
  })
}

export const createUser = (data) => {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

export const updateUser = (id, data) => {
  return request({
    url: `/users/${id}`,
    method: 'put',
    data
  })
}

export const deleteUser = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

export const resetPassword = (id) => {
  return request({
    url: `/users/${id}/reset-password`,
    method: 'post'
  })
}

export const updateUserStatus = (id, status) => {
  return request({
    url: `/users/${id}/status`,
    method: 'put',
    data: { status }
  })
}

export const assignRoles = (userId, roleIds) => {
  return request({
    url: `/users/${userId}/roles`,
    method: 'put',
    data: { roleIds }
  })
}
