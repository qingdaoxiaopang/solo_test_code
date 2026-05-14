<template>
  <div class="device-detail-container">
    <van-nav-bar
      title="设备详情"
      left-text="返回"
      right-text="编辑"
      left-arrow
      @click-left="handleBack"
      @click-right="handleEdit"
    />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <div class="detail-content" v-if="device">
        <div class="detail-header">
          <div class="device-status">
            <van-tag :type="getStatusType(device.status)" size="large">
              {{ getStatusText(device.status) }}
            </van-tag>
          </div>
          <h2>{{ device.name }}</h2>
          <p class="device-code">编号: {{ device.code }}</p>
        </div>

        <div class="detail-section">
          <h3>基本信息</h3>
          <van-cell-group inset>
            <van-cell title="设备类型" :value="device.typeName || '-'" />
            <van-cell title="设备型号" :value="device.modelName || '-'" />
            <van-cell title="生产厂商" :value="device.manufacturer || '-'" />
            <van-cell title="安装位置" :value="device.location || '-'" />
            <van-cell title="所属专题" :value="device.topicName || '-'" />
            <van-cell title="购入日期" :value="formatDate(device.purchaseDate)" />
          </van-cell-group>
        </div>

        <div class="detail-section">
          <h3>实时监控</h3>
          <van-cell-group inset>
            <van-cell title="运行时长" :value="device.runningHours || '0小时'" />
            <van-cell title="故障次数" :value="device.faultCount || 0" />
            <van-cell title="最后巡检" :value="formatDate(device.lastInspection)" />
            <van-cell title="最后维护" :value="formatDate(device.lastMaintenance)" />
          </van-cell-group>
        </div>

        <div class="detail-section">
          <h3>快捷操作</h3>
          <van-cell-group inset>
            <van-cell title="故障上报" is-link @click="reportFault">
              <template #icon>
                <van-icon name="warning-o" color="#ff4d4f" style="margin-right: 12px;" />
              </template>
            </van-cell>
            <van-cell title="创建维护工单" is-link @click="createMaintenance">
              <template #icon>
                <van-icon name="setting-o" color="#52c41a" style="margin-right: 12px;" />
              </template>
            </van-cell>
            <van-cell title="开始巡检" is-link @click="startInspection">
              <template #icon>
                <van-icon name="todo-list-o" color="#1890ff" style="margin-right: 12px;" />
              </template>
            </van-cell>
          </van-cell-group>
        </div>

        <div class="detail-section">
          <h3>操作记录</h3>
          <van-cell-group inset>
            <van-cell
              v-for="record in records"
              :key="record.id"
              :title="record.title"
              :label="record.description"
              :value="formatDateTime(record.createTime)"
            />
          </van-cell-group>
        </div>
      </div>

      <van-empty v-else description="设备不存在" />
    </van-pull-refresh>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getDeviceById } from '@/api/devices'
import { formatDate, formatDateTime } from '@/utils/format'
import { Dialog, Toast } from 'vant'

const route = useRoute()
const router = useRouter()
const deviceId = computed(() => route.params.id)

const refreshing = ref(false)
const device = ref(null)
const records = ref([])

const getStatusType = (status) => {
  const map = { online: 'success', offline: 'info', fault: 'danger', maintenance: 'warning' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { online: '在线', offline: '离线', fault: '故障', maintenance: '维护中' }
  return map[status] || status
}

const loadData = async () => {
  try {
    const res = await getDeviceById(deviceId.value)
    device.value = res.data || res
    records.value = device.value.records || []
  } catch (error) {
    console.error('Load device error:', error)
  }
}

const onRefresh = async () => {
  await loadData()
  refreshing.value = false
}

const handleBack = () => {
  router.back()
}

const handleEdit = () => {
  router.push(`/device/form?id=${deviceId.value}`)
}

const reportFault = () => {
  router.push(`/device/faults?deviceId=${deviceId.value}&action=report`)
}

const createMaintenance = () => {
  router.push(`/device/maintenance-orders?deviceId=${deviceId.value}&action=create`)
}

const startInspection = () => {
  Dialog.confirm({
    title: '开始巡检',
    message: '确认开始对设备进行巡检？',
    confirmText: '开始巡检'
  }).then(() => {
    Toast.success('巡检已开始')
  })
}

onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.device-detail-container {
  min-height: 100%;
  background: #f7f8fa;

  .detail-content {
    padding: 16px;

    .detail-header {
      background: #fff;
      border-radius: 12px;
      padding: 20px;
      margin-bottom: 16px;
      text-align: center;

      .device-status {
        margin-bottom: 12px;
      }

      h2 {
        font-size: 20px;
        font-weight: 600;
        color: #333;
        margin-bottom: 8px;
      }

      .device-code {
        font-size: 14px;
        color: #999;
      }
    }

    .detail-section {
      background: #fff;
      border-radius: 12px;
      padding: 16px;
      margin-bottom: 16px;

      h3 {
        font-size: 16px;
        font-weight: 600;
        color: #333;
        margin-bottom: 12px;
      }
    }
  }
}
</style>
