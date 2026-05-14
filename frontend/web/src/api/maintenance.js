import request from './request'

export const getMaintenanceOrderList = (params) => {
  return request({
    url: '/maintenance/orders',
    method: 'get',
    params
  })
}

export const getMaintenanceOrderById = (id) => {
  return request({
    url: `/maintenance/orders/${id}`,
    method: 'get'
  })
}

export const createMaintenanceOrder = (data) => {
  return request({
    url: '/maintenance/orders',
    method: 'post',
    data
  })
}

export const updateMaintenanceOrder = (id, data) => {
  return request({
    url: `/maintenance/orders/${id}`,
    method: 'put',
    data
  })
}

export const deleteMaintenanceOrder = (id) => {
  return request({
    url: `/maintenance/orders/${id}`,
    method: 'delete'
  })
}

export const assignMaintenanceOrder = (id, assigneeId) => {
  return request({
    url: `/maintenance/orders/${id}/assign`,
    method: 'put',
    data: { assigneeId }
  })
}

export const startMaintenanceOrder = (id) => {
  return request({
    url: `/maintenance/orders/${id}/start`,
    method: 'put'
  })
}

export const pauseMaintenanceOrder = (id, reason) => {
  return request({
    url: `/maintenance/orders/${id}/pause`,
    method: 'put',
    data: { reason }
  })
}

export const resumeMaintenanceOrder = (id) => {
  return request({
    url: `/maintenance/orders/${id}/resume`,
    method: 'put'
  })
}

export const completeMaintenanceOrder = (id, data) => {
  return request({
    url: `/maintenance/orders/${id}/complete`,
    method: 'put',
    data
  })
}

export const closeMaintenanceOrder = (id, data) => {
  return request({
    url: `/maintenance/orders/${id}/close`,
    method: 'put',
    data
  })
}

export const cancelMaintenanceOrder = (id, reason) => {
  return request({
    url: `/maintenance/orders/${id}/cancel`,
    method: 'put',
    data: { reason }
  })
}

export const addMaintenanceRecord = (id, data) => {
  return request({
    url: `/maintenance/orders/${id}/records`,
    method: 'post',
    data
  })
}

export const getMaintenanceRecords = (id, params) => {
  return request({
    url: `/maintenance/orders/${id}/records`,
    method: 'get',
    params
  })
}

export const uploadMaintenanceAttachment = (id, data) => {
  return request({
    url: `/maintenance/orders/${id}/attachments`,
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const getMaintenanceStats = (params) => {
  return request({
    url: '/maintenance/stats',
    method: 'get',
    params
  })
}

export const getMaintenanceTemplates = () => {
  return request({
    url: '/maintenance/templates',
    method: 'get'
  })
}

export const getSparePartList = (params) => {
  return request({
    url: '/maintenance/spare-parts',
    method: 'get',
    params
  })
}

export const getSparePartById = (id) => {
  return request({
    url: `/maintenance/spare-parts/${id}`,
    method: 'get'
  })
}

export const createSparePart = (data) => {
  return request({
    url: '/maintenance/spare-parts',
    method: 'post',
    data
  })
}

export const updateSparePart = (id, data) => {
  return request({
    url: `/maintenance/spare-parts/${id}`,
    method: 'put',
    data
  })
}

export const deleteSparePart = (id) => {
  return request({
    url: `/maintenance/spare-parts/${id}`,
    method: 'delete'
  })
}

export const recordSparePartUsage = (id, data) => {
  return request({
    url: `/maintenance/spare-parts/${id}/usage`,
    method: 'post',
    data
  })
}

export const getSparePartStock = (params) => {
  return request({
    url: '/maintenance/spare-parts/stock',
    method: 'get',
    params
  })
}

export const getMaintenanceCompanies = (params) => {
  return request({
    url: '/maintenance/companies',
    method: 'get',
    params
  })
}

export const createMaintenanceCompany = (data) => {
  return request({
    url: '/maintenance/companies',
    method: 'post',
    data
  })
}

export const updateMaintenanceCompany = (id, data) => {
  return request({
    url: `/maintenance/companies/${id}`,
    method: 'put',
    data
  })
}

export const deleteMaintenanceCompany = (id) => {
  return request({
    url: `/maintenance/companies/${id}`,
    method: 'delete'
  })
}
