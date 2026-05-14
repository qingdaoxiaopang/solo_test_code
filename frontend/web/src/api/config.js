import request from './request'

export const getDeviceTypes = (params) => {
  return request({
    url: '/config/device-types',
    method: 'get',
    params
  })
}

export const getDeviceTypeById = (id) => {
  return request({
    url: `/config/device-types/${id}`,
    method: 'get'
  })
}

export const createDeviceType = (data) => {
  return request({
    url: '/config/device-types',
    method: 'post',
    data
  })
}

export const updateDeviceType = (id, data) => {
  return request({
    url: `/config/device-types/${id}`,
    method: 'put',
    data
  })
}

export const deleteDeviceType = (id) => {
  return request({
    url: `/config/device-types/${id}`,
    method: 'delete'
  })
}

export const getManufacturers = (params) => {
  return request({
    url: '/config/manufacturers',
    method: 'get',
    params
  })
}

export const getManufacturerById = (id) => {
  return request({
    url: `/config/manufacturers/${id}`,
    method: 'get'
  })
}

export const createManufacturer = (data) => {
  return request({
    url: '/config/manufacturers',
    method: 'post',
    data
  })
}

export const updateManufacturer = (id, data) => {
  return request({
    url: `/config/manufacturers/${id}`,
    method: 'put',
    data
  })
}

export const deleteManufacturer = (id) => {
  return request({
    url: `/config/manufacturers/${id}`,
    method: 'delete'
  })
}

export const getDeviceModels = (params) => {
  return request({
    url: '/config/device-models',
    method: 'get',
    params
  })
}

export const getDeviceModelById = (id) => {
  return request({
    url: `/config/device-models/${id}`,
    method: 'get'
  })
}

export const createDeviceModel = (data) => {
  return request({
    url: '/config/device-models',
    method: 'post',
    data
  })
}

export const updateDeviceModel = (id, data) => {
  return request({
    url: `/config/device-models/${id}`,
    method: 'put',
    data
  })
}

export const deleteDeviceModel = (id) => {
  return request({
    url: `/config/device-models/${id}`,
    method: 'delete'
  })
}

export const getTopics = (params) => {
  return request({
    url: '/config/topics',
    method: 'get',
    params
  })
}

export const getTopicById = (id) => {
  return request({
    url: `/config/topics/${id}`,
    method: 'get'
  })
}

export const createTopic = (data) => {
  return request({
    url: '/config/topics',
    method: 'post',
    data
  })
}

export const updateTopic = (id, data) => {
  return request({
    url: `/config/topics/${id}`,
    method: 'put',
    data
  })
}

export const deleteTopic = (id) => {
  return request({
    url: `/config/topics/${id}`,
    method: 'delete'
  })
}

export const getRoles = (params) => {
  return request({
    url: '/config/roles',
    method: 'get',
    params
  })
}

export const getRoleById = (id) => {
  return request({
    url: `/config/roles/${id}`,
    method: 'get'
  })
}

export const createRole = (data) => {
  return request({
    url: '/config/roles',
    method: 'post',
    data
  })
}

export const updateRole = (id, data) => {
  return request({
    url: `/config/roles/${id}`,
    method: 'put',
    data
  })
}

export const deleteRole = (id) => {
  return request({
    url: `/config/roles/${id}`,
    method: 'delete'
  })
}

export const getPermissions = () => {
  return request({
    url: '/config/permissions',
    method: 'get'
  })
}

export const getDicts = (params) => {
  return request({
    url: '/config/dicts',
    method: 'get',
    params
  })
}

export const getDictByCode = (code) => {
  return request({
    url: `/config/dicts/${code}`,
    method: 'get'
  })
}

export const createDict = (data) => {
  return request({
    url: '/config/dicts',
    method: 'post',
    data
  })
}

export const updateDict = (id, data) => {
  return request({
    url: `/config/dicts/${id}`,
    method: 'put',
    data
  })
}

export const deleteDict = (id) => {
  return request({
    url: `/config/dicts/${id}`,
    method: 'delete'
  })
}

export const getDictItems = (dictId) => {
  return request({
    url: `/config/dicts/${dictId}/items`,
    method: 'get'
  })
}

export const createDictItem = (dictId, data) => {
  return request({
    url: `/config/dicts/${dictId}/items`,
    method: 'post',
    data
  })
}

export const updateDictItem = (dictId, itemId, data) => {
  return request({
    url: `/config/dicts/${dictId}/items/${itemId}`,
    method: 'put',
    data
  })
}

export const deleteDictItem = (dictId, itemId) => {
  return request({
    url: `/config/dicts/${dictId}/items/${itemId}`,
    method: 'delete'
  })
}

export const getLogs = (params) => {
  return request({
    url: '/config/logs',
    method: 'get',
    params
  })
}

export const getLogById = (id) => {
  return request({
    url: `/config/logs/${id}`,
    method: 'get'
  })
}

export const clearLogs = (type) => {
  return request({
    url: '/config/logs/clear',
    method: 'delete',
    data: { type }
  })
}

export const getSystemSettings = () => {
  return request({
    url: '/config/settings',
    method: 'get'
  })
}

export const updateSystemSettings = (data) => {
  return request({
    url: '/config/settings',
    method: 'put',
    data
  })
}
