<template>
  <div class="device-list-container">
    <van-search
      v-model="searchQuery"
      placeholder="搜索设备名称/编号"
      @search="handleSearch"
    />
    
    <van-dropdown-menu>
      <van-dropdown-item
        v-model="filterTopic"
        :options="topicOptions"
        @change="handleFilterChange"
      />
      <van-dropdown-item
        v-model="filterStatus"
        :options="statusOptions"
        @change="handleFilterChange"
      />
    </van-dropdown-menu>

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="device-list">
          <div
            v-for="device in deviceList"
            :key="device.id"
            class="device-card"
            @click="goToDetail(device)"
          >
            <div class="device-header">
              <div class="device-info">
                <h3 class="device-name">{{ device.name }}</h3>
                <span class="device-code">{{ device.code }}</span>
              </div>
              <van-tag :type="getStatusType(device.status)" size="medium">
                {{ getStatusText(device.status) }}
              </van-tag>
            </div>
            <div class="device-meta">
              <span class="meta-item">
                <van-icon name="location-o" />
                {{ device.location || '未设置位置' }}
              </span>
              <span class="meta-item">
                <van-icon name="cluster-o" />
                {{ device.topicName || '默认专题' }}
              </span>
            </div>
            <div class="device-footer">
              <span class="update-time">更新时间: {{ formatRelativeTime(device.updateTime) }}</span>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

    <div class="add-button" v-if="hasPermission('device:create')">
      <van-button round type="primary" icon="plus" @click="goToForm" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getDeviceList } from '@/api/devices'
import { formatRelativeTime } from '@/utils/format'
import { Toast } from 'vant'

const router = useRouter()
const userStore = useUserStore()

const searchQuery = ref('')
const filterTopic = ref('')
const filterStatus = ref('')
const refreshing = ref(false)
const loading = ref(false)
const finished = ref(false)
const deviceList = ref([])
const page = ref(1)
const pageSize = ref(10)

const topicOptions = ref([
  { text: '全部专题', value: '' },
  { text: '电梯', value: 'elevator' },
  { text: '空调', value: 'hvac' },
  { text: '消防', value: 'fire' },
  { text: '安防', value: 'security' }
])

const statusOptions = ref([
  { text: '全部状态', value: '' },
  { text: '在线', value: 'online' },
  { text: '离线', value: 'offline' },
  { text: '故障', value: 'fault' },
  { text: '维护中', value: 'maintenance' }
])

const hasPermission = (permission) => {
  return userStore.hasPermission(permission)
}

const getStatusType = (status) => {
  const map = { online: 'success', offline: 'info', fault: 'danger', maintenance: 'warning' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { online: '在线', offline: '离线', fault: '故障', maintenance: '维护中' }
  return map[status] || status
}

const onLoad = async () => {
  try {
    const res = await getDeviceList({
      page: page.value,
      pageSize: pageSize.value,
      keyword: searchQuery.value,
      topic: filterTopic.value,
      status: filterStatus.value
    })
    const data = res.data || res
    deviceList.value = [...deviceList.value, ...data.list || data]
    page.value++
    if (page.value > (data.totalPage || 1)) {
      finished.value = true
    }
  } catch (error) {
    console.error('Load devices error:', error)
  } finally {
    loading.value = false
  }
}

const onRefresh = async () => {
  page.value = 1
  finished.value = false
  deviceList.value = []
  await onLoad()
  refreshing.value = false
}

const handleSearch = () => {
  onRefresh()
}

const handleFilterChange = () => {
  onRefresh()
}

const goToDetail = (device) => {
  router.push(`/device/detail/${device.id}`)
}

const goToForm = () => {
  router.push('/device/form')
}

onMounted(() => {
  onRefresh()
})
</script>

<style lang="scss" scoped>
.device-list-container {
  min-height: 100%;
  background: #f7f8fa;
  padding-bottom: 80px;

  .device-list {
    padding: 16px;
  }

  .device-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;
    cursor: pointer;
    transition: all 0.3s;

    &:active {
      transform: scale(0.98);
    }

    .device-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 12px;

      .device-info {
        flex: 1;

        .device-name {
          font-size: 16px;
          font-weight: 600;
          color: #333;
          margin-bottom: 4px;
        }

        .device-code {
          font-size: 13px;
          color: #999;
        }
      }
    }

    .device-meta {
      display: flex;
      flex-wrap: wrap;
      gap: 16px;
      margin-bottom: 12px;

      .meta-item {
        display: flex;
        align-items: center;
        gap: 4px;
        font-size: 13px;
        color: #666;

        :deep(.van-icon) {
          color: #999;
        }
      }
    }

    .device-footer {
      padding-top: 12px;
      border-top: 1px solid #f5f5f5;

      .update-time {
        font-size: 12px;
        color: #999;
      }
    }
  }

  .add-button {
    position: fixed;
    bottom: 70px;
    right: 16px;
    z-index: 100;
  }
}
</style>
