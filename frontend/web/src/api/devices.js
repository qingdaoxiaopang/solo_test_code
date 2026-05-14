import request from './request'

export const getDeviceList = (params) => {
  return request({
    url: '/devices',
    method: 'get',
    params
  })
}

export const getDeviceById = (id) => {
  return request({
    url: `/devices/${id}`,
    method: 'get'
  })
}

export const createDevice = (data) => {
  return request({
    url: '/devices',
    method: 'post',
    data
  })
}

export const updateDevice = (id, data) => {
  return request({
    url: `/devices/${id}`,
    method: 'put',
    data
  })
}

export const deleteDevice = (id) => {
  return request({
    url: `/devices/${id}`,
    method: 'delete'
  })
}

export const getDeviceDetail = (id) => {
  return request({
    url: `/devices/${id}/detail`,
    method: 'get'
  })
}

export const updateDeviceStatus = (id, status) => {
  return request({
    url: `/devices/${id}/status`,
    method: 'put',
    data: { status }
  })
}

export const getDeviceMonitor = (id) => {
  return request({
    url: `/devices/${id}/monitor`,
    method: 'get'
  })
}

export const getDeviceLogs = (id, params) => {
  return request({
    url: `/devices/${id}/logs`,
    method: 'get',
    params
  })
}

export const exportDevices = (params) => {
  return request({
    url: '/devices/export',
    method: 'get',
    params,
    responseType: 'blob'
  })
}

export const importDevices = (data) => {
  return request({
    url: '/devices/import',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const getDeviceMapData = (params) => {
  return request({
    url: '/devices/map',
    method: 'get',
    params
  })
}

export const getDeviceStats = (params) => {
  return request({
    url: '/devices/stats',
    method: 'get',
    params
  })
}

export const batchUpdateDevice = (ids, data) => {
  return request({
    url: '/devices/batch',
    method: 'put',
    data: { ids, ...data }
  })
}
