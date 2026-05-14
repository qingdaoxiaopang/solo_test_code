<template>
  <div class="faults-container">
    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="全部" name="all"></van-tab>
      <van-tab title="待处理" name="pending"></van-tab>
      <van-tab title="处理中" name="processing"></van-tab>
      <van-tab title="已解决" name="resolved"></van-tab>
    </van-tabs>

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="fault-list">
          <div
            v-for="fault in faults"
            :key="fault.id"
            class="fault-card"
            @click="goToDetail(fault)"
          >
            <div class="fault-header">
              <h3>{{ fault.title }}</h3>
              <van-tag :type="getStatusType(fault.level)">
                {{ getLevelText(fault.level) }}
              </van-tag>
            </div>
            <div class="fault-info">
              <span><van-icon name="apps-o" /> {{ fault.deviceName }}</span>
              <span><van-icon name="location-o" /> {{ fault.location }}</span>
            </div>
            <div class="fault-footer">
              <span class="status-tag">
                <van-tag :type="getStatusType2(fault.status)">
                  {{ getStatusText(fault.status) }}
                </van-tag>
              </span>
              <span>{{ formatDateTime(fault.reportTime) }}</span>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

    <div class="add-button">
      <van-button round type="danger" icon="warning" @click="handleReport" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { formatDateTime } from '@/utils/format'

const router = useRouter()
const activeTab = ref('all')
const refreshing = ref(false)
const loading = ref(false)
const finished = ref(false)

const faults = ref([
  { id: 1, title: '电梯门无法关闭', deviceName: 'B栋3号电梯', location: 'B栋3层', level: 'high', status: 'pending', reportTime: '2024-01-15 08:30' },
  { id: 2, title: '空调无法制冷', deviceName: 'A栋中央空调', location: 'A栋1层', level: 'medium', status: 'processing', reportTime: '2024-01-14 15:20' },
  { id: 3, title: '消防报警器故障', deviceName: 'A栋5层消防', location: 'A栋5层', level: 'low', status: 'resolved', reportTime: '2024-01-13 10:00' }
])

const getStatusType = (level) => {
  const map = { high: 'danger', medium: 'warning', low: 'primary' }
  return map[level] || 'info'
}

const getLevelText = (level) => {
  const map = { high: '紧急', medium: '一般', low: '轻微' }
  return map[level] || level
}

const getStatusType2 = (status) => {
  const map = { pending: 'warning', processing: 'primary', resolved: 'success', closed: 'info' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { pending: '待处理', processing: '处理中', resolved: '已解决', closed: '已关闭' }
  return map[status] || status
}

const onRefresh = () => {
  refreshing.value = false
}

const onLoad = () => {
  finished.value = true
  loading.value = false
}

const goToDetail = (fault) => {
}

const handleReport = () => {
}
</script>

<style lang="scss" scoped>
.faults-container {
  min-height: 100%;
  background: #f7f8fa;
  padding-bottom: 80px;

  .fault-list {
    padding: 16px;
  }

  .fault-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;

    .fault-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 12px;

      h3 {
        font-size: 16px;
        font-weight: 600;
        color: #333;
      }
    }

    .fault-info {
      display: flex;
      flex-direction: column;
      gap: 8px;
      margin-bottom: 12px;

      span {
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

    .fault-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-top: 12px;
      border-top: 1px solid #f5f5f5;

      span {
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
