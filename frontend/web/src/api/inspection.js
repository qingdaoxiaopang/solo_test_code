import request from './request'

export const getInspectionPlanList = (params) => {
  return request({
    url: '/inspections/plans',
    method: 'get',
    params
  })
}

export const getInspectionPlanById = (id) => {
  return request({
    url: `/inspections/plans/${id}`,
    method: 'get'
  })
}

export const createInspectionPlan = (data) => {
  return request({
    url: '/inspections/plans',
    method: 'post',
    data
  })
}

export const updateInspectionPlan = (id, data) => {
  return request({
    url: `/inspections/plans/${id}`,
    method: 'put',
    data
  })
}

export const deleteInspectionPlan = (id) => {
  return request({
    url: `/inspections/plans/${id}`,
    method: 'delete'
  })
}

export const enableInspectionPlan = (id) => {
  return request({
    url: `/inspections/plans/${id}/enable`,
    method: 'put'
  })
}

export const disableInspectionPlan = (id) => {
  return request({
    url: `/inspections/plans/${id}/disable`,
    method: 'put'
  })
}

export const getInspectionTaskList = (params) => {
  return request({
    url: '/inspections/tasks',
    method: 'get',
    params
  })
}

export const getInspectionTaskById = (id) => {
  return request({
    url: `/inspections/tasks/${id}`,
    method: 'get'
  })
}

export const createInspectionTask = (data) => {
  return request({
    url: '/inspections/tasks',
    method: 'post',
    data
  })
}

export const startInspectionTask = (id) => {
  return request({
    url: `/inspections/tasks/${id}/start`,
    method: 'put'
  })
}

export const submitInspectionTask = (id, data) => {
  return request({
    url: `/inspections/tasks/${id}/submit`,
    method: 'put',
    data
  })
}

export const approveInspectionTask = (id, data) => {
  return request({
    url: `/inspections/tasks/${id}/approve`,
    method: 'put',
    data
  })
}

export const getInspectionRecordList = (params) => {
  return request({
    url: '/inspections/records',
    method: 'get',
    params
  })
}

export const getInspectionRecordById = (id) => {
  return request({
    url: `/inspections/records/${id}`,
    method: 'get'
  })
}

export const getInspectionStats = (params) => {
  return request({
    url: '/inspections/stats',
    method: 'get',
    params
  })
}

export const getInspectionTemplates = () => {
  return request({
    url: '/inspections/templates',
    method: 'get'
  })
}

export const createInspectionTemplate = (data) => {
  return request({
    url: '/inspections/templates',
    method: 'post',
    data
  })
}

export const updateInspectionTemplate = (id, data) => {
  return request({
    url: `/inspections/templates/${id}`,
    method: 'put',
    data
  })
}

export const deleteInspectionTemplate = (id) => {
  return request({
    url: `/inspections/templates/${id}`,
    method: 'delete'
  })
}
