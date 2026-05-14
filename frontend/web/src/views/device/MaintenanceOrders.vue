<template>
  <div class="maintenance-orders-container">
    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="全部" name="all"></van-tab>
      <van-tab title="待处理" name="pending"></van-tab>
      <van-tab title="处理中" name="processing"></van-tab>
      <van-tab title="已完成" name="completed"></van-tab>
    </van-tabs>

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="order-list">
          <div
            v-for="order in orders"
            :key="order.id"
            class="order-card"
            @click="goToDetail(order)"
          >
            <div class="order-header">
              <h3>{{ order.title }}</h3>
              <van-tag :type="getStatusType(order.status)">
                {{ getStatusText(order.status) }}
              </van-tag>
            </div>
            <div class="order-info">
              <span><van-icon name="apps-o" /> {{ order.deviceName }}</span>
              <span><van-icon name="user-o" /> {{ order.assigneeName || '待分配' }}</span>
            </div>
            <div class="order-footer">
              <span>{{ formatDateTime(order.createTime) }}</span>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

    <div class="add-button">
      <van-button round type="primary" icon="plus" @click="handleCreate" />
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

const orders = ref([
  { id: 1, title: '电梯保养维护', deviceName: 'B栋3号电梯', assigneeName: '张三', status: 'pending', createTime: '2024-01-15 10:30' },
  { id: 2, title: '空调滤网更换', deviceName: 'A栋中央空调', assigneeName: '李四', status: 'processing', createTime: '2024-01-14 14:20' },
  { id: 3, title: '消防设备检查', deviceName: 'A栋5层消防', assigneeName: '王五', status: 'completed', createTime: '2024-01-13 09:00' }
])

const getStatusType = (status) => {
  const map = { pending: 'warning', processing: 'primary', completed: 'success', closed: 'info' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { pending: '待处理', processing: '处理中', completed: '已完成', closed: '已关闭' }
  return map[status] || status
}

const onRefresh = () => {
  refreshing.value = false
}

const onLoad = () => {
  finished.value = true
  loading.value = false
}

const goToDetail = (order) => {
}

const handleCreate = () => {
}
</script>

<style lang="scss" scoped>
.maintenance-orders-container {
  min-height: 100%;
  background: #f7f8fa;
  padding-bottom: 80px;

  .order-list {
    padding: 16px;
  }

  .order-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;

    .order-header {
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

    .order-info {
      display: flex;
      gap: 16px;
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

    .order-footer {
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
