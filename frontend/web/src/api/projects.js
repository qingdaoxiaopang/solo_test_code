import request from './request'

export const getProjectList = (params) => {
  return request({
    url: '/projects',
    method: 'get',
    params
  })
}

export const getProjectById = (id) => {
  return request({
    url: `/projects/${id}`,
    method: 'get'
  })
}

export const createProject = (data) => {
  return request({
    url: '/projects',
    method: 'post',
    data
  })
}

export const updateProject = (id, data) => {
  return request({
    url: `/projects/${id}`,
    method: 'put',
    data
  })
}

export const deleteProject = (id) => {
  return request({
    url: `/projects/${id}`,
    method: 'delete'
  })
}

export const getProjectStats = (id) => {
  return request({
    url: `/projects/${id}/stats`,
    method: 'get'
  })
}

export const getProjectDevices = (id, params) => {
  return request({
    url: `/projects/${id}/devices`,
    method: 'get',
    params
  })
}

export const getProjectUsers = (id) => {
  return request({
    url: `/projects/${id}/users`,
    method: 'get'
  })
}

export const addProjectUser = (id, userId, role) => {
  return request({
    url: `/projects/${id}/users`,
    method: 'post',
    data: { userId, role }
  })
}

export const removeProjectUser = (id, userId) => {
  return request({
    url: `/projects/${id}/users/${userId}`,
    method: 'delete'
  })
}

export const getMyProjects = () => {
  return request({
    url: '/projects/my',
    method: 'get'
  })
}

export const switchProject = (id) => {
  return request({
    url: `/projects/${id}/switch`,
    method: 'post'
  })
}
