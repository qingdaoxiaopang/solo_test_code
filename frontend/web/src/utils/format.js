import dayjs from 'dayjs'

export const formatDate = (date, format = 'YYYY-MM-DD') => {
  if (!date) return ''
  return dayjs(date).format(format)
}

export const formatDateTime = (date, format = 'YYYY-MM-DD HH:mm:ss') => {
  if (!date) return ''
  return dayjs(date).format(format)
}

export const formatTime = (date, format = 'HH:mm:ss') => {
  if (!date) return ''
  return dayjs(date).format(format)
}

export const formatRelativeTime = (date) => {
  if (!date) return ''
  const now = dayjs()
  const target = dayjs(date)
  const diff = now.diff(target, 'minute')
  
  if (diff < 1) return '刚刚'
  if (diff < 60) return `${diff}分钟前`
  
  const hours = Math.floor(diff / 60)
  if (hours < 24) return `${hours}小时前`
  
  const days = Math.floor(hours / 24)
  if (days < 30) return `${days}天前`
  
  const months = Math.floor(days / 30)
  if (months < 12) return `${months}月前`
  
  const years = Math.floor(months / 12)
  return `${years}年前`
}

export const formatNumber = (num, decimals = 0) => {
  if (num === null || num === undefined) return ''
  const number = Number(num)
  if (isNaN(number)) return ''
  return number.toFixed(decimals)
}

export const formatFileSize = (bytes) => {
  if (!bytes || bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i]
}

export const formatDuration = (seconds) => {
  if (!seconds) return '0秒'
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  const secs = seconds % 60
  
  const parts = []
  if (hours > 0) parts.push(`${hours}小时`)
  if (minutes > 0) parts.push(`${minutes}分钟`)
  if (secs > 0 || parts.length === 0) parts.push(`${secs}秒`)
  
  return parts.join('')
}

export const formatPhone = (phone) => {
  if (!phone) return ''
  return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1****$3')
}

export const formatIdCard = (idCard) => {
  if (!idCard) return ''
  return idCard.replace(/(\d{6})\d{8}(\d{4})/, '$1********$2')
}

export const formatBankCard = (card) => {
  if (!card) return ''
  return card.replace(/(\d{4})\d+(\d{4})/, '$1****$2')
}

export const truncate = (str, length = 50) => {
  if (!str) return ''
  if (str.length <= length) return str
  return str.substring(0, length) + '...'
}

export const formatStatus = (status, type = 'default') => {
  const statusMap = {
    default: { pending: '待处理', processing: '处理中', completed: '已完成', cancelled: '已取消' },
    device: { online: '在线', offline: '离线', fault: '故障', maintenance: '维护中' },
    order: { pending: '待派单', assigned: '已派单', processing: '处理中', completed: '已完成', closed: '已关闭' },
    inspection: { pending: '待巡检', processing: '巡检中', completed: '已完成', overdue: '已逾期' }
  }
  
  const map = statusMap[type] || statusMap.default
  return map[status] || status
}

export const getStatusType = (status) => {
  const typeMap = {
    online: 'success',
    offline: 'info',
    fault: 'danger',
    maintenance: 'warning',
    pending: 'info',
    processing: 'primary',
    completed: 'success',
    cancelled: 'info',
    overdue: 'danger',
    assigned: 'primary'
  }
  return typeMap[status] || 'default'
}
