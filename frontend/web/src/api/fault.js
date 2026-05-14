import request from './request'

export const getFaultList = (params) => {
  return request({
    url: '/faults',
    method: 'get',
    params
  })
}

export const getFaultById = (id) => {
  return request({
    url: `/faults/${id}`,
    method: 'get'
  })
}

export const createFault = (data) => {
  return request({
    url: '/faults',
    method: 'post',
    data
  })
}

export const updateFault = (id, data) => {
  return request({
    url: `/faults/${id}`,
    method: 'put',
    data
  })
}

export const deleteFault = (id) => {
  return request({
    url: `/faults/${id}`,
    method: 'delete'
  })
}

export const reportFault = (data) => {
  return request({
    url: '/faults/report',
    method: 'post',
    data
  })
}

export const assignFault = (id, assigneeId) => {
  return request({
    url: `/faults/${id}/assign`,
    method: 'put',
    data: { assigneeId }
  })
}

export const startFaultProcess = (id) => {
  return request({
    url: `/faults/${id}/start`,
    method: 'put'
  })
}

export const analyzeFault = (id, data) => {
  return request({
    url: `/faults/${id}/analyze`,
    method: 'put',
    data
  })
}

export const handleFault = (id, data) => {
  return request({
    url: `/faults/${id}/handle`,
    method: 'put',
    data
  })
}

export const verifyFault = (id, data) => {
  return request({
    url: `/faults/${id}/verify`,
    method: 'put',
    data
  })
}

export const closeFault = (id, data) => {
  return request({
    url: `/faults/${id}/close`,
    method: 'put',
    data
  })
}

export const cancelFault = (id, reason) => {
  return request({
    url: `/faults/${id}/cancel`,
    method: 'put',
    data: { reason }
  })
}

export const addFaultRecord = (id, data) => {
  return request({
    url: `/faults/${id}/records`,
    method: 'post',
    data
  })
}

export const getFaultRecords = (id, params) => {
  return request({
    url: `/faults/${id}/records`,
    method: 'get',
    params
  })
}

export const uploadFaultAttachment = (id, data) => {
  return request({
    url: `/faults/${id}/attachments`,
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const getFaultStats = (params) => {
  return request({
    url: '/faults/stats',
    method: 'get',
    params
  })
}

export const getFaultTypes = () => {
  return request({
    url: '/faults/types',
    method: 'get'
  })
}

export const getFaultAnalysis = (params) => {
  return request({
    url: '/faults/analysis',
    method: 'get',
    params
  })
}

export const getFaultTrend = (params) => {
  return request({
    url: '/faults/trend',
    method: 'get',
    params
  })
}
