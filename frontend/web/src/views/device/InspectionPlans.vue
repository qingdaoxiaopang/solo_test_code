<template>
  <div class="inspection-plans-container">
    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="巡检计划" name="plan">
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
          <van-list
            v-model:loading="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad"
          >
            <div class="plan-list">
              <div
                v-for="plan in plans"
                :key="plan.id"
                class="plan-card"
                @click="goToDetail(plan)"
              >
                <div class="plan-header">
                  <h3>{{ plan.name }}</h3>
                  <van-tag :type="plan.enabled ? 'success' : 'info'">
                    {{ plan.enabled ? '已启用' : '已禁用' }}
                  </van-tag>
                </div>
                <div class="plan-info">
                  <span><van-icon name="clock-o" /> {{ plan.frequency }}</span>
                  <span><van-icon name="apps-o" /> {{ plan.deviceCount }}台设备</span>
                </div>
                <div class="plan-footer">
                  <span>下次执行: {{ plan.nextRunTime }}</span>
                </div>
              </div>
            </div>
          </van-list>
        </van-pull-refresh>
      </van-tab>
      <van-tab title="巡检任务" name="task">
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
          <van-list
            v-model:loading="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad"
          >
            <van-cell-group inset>
              <van-cell
                v-for="task in tasks"
                :key="task.id"
                :title="task.name"
                :label="task.deviceName"
                is-link
                @click="goToTask(task)"
              >
                <template #value>
                  <van-tag :type="getStatusType(task.status)">
                    {{ getStatusText(task.status) }}
                  </van-tag>
                </template>
              </van-cell>
            </van-cell-group>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>

    <div class="add-button">
      <van-button round type="primary" icon="plus" @click="handleCreate" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeTab = ref('plan')
const refreshing = ref(false)
const loading = ref(false)
const finished = ref(false)

const plans = ref([
  { id: 1, name: '电梯月度巡检', frequency: '每月一次', deviceCount: 24, nextRunTime: '2024-02-01', enabled: true },
  { id: 2, name: '消防设备巡检', frequency: '每周一次', deviceCount: 48, nextRunTime: '2024-01-20', enabled: true },
  { id: 3, name: '空调系统巡检', frequency: '每季度一次', deviceCount: 16, nextRunTime: '2024-03-01', enabled: false }
])

const tasks = ref([
  { id: 1, name: '3号电梯巡检', deviceName: 'B栋3号电梯', status: 'pending' },
  { id: 2, name: '5层消防巡检', deviceName: 'A栋5层消防', status: 'processing' },
  { id: 3, name: '中央空调巡检', deviceName: 'A栋中央空调', status: 'completed' }
])

const getStatusType = (status) => {
  const map = { pending: 'warning', processing: 'primary', completed: 'success', overdue: 'danger' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { pending: '待巡检', processing: '巡检中', completed: '已完成', overdue: '已逾期' }
  return map[status] || status
}

const onRefresh = () => {
  refreshing.value = false
}

const onLoad = () => {
  finished.value = true
  loading.value = false
}

const goToDetail = (plan) => {
}

const goToTask = (task) => {
}

const handleCreate = () => {
}
</script>

<style lang="scss" scoped>
.inspection-plans-container {
  min-height: 100%;
  background: #f7f8fa;
  padding-bottom: 80px;

  .plan-list {
    padding: 16px;
  }

  .plan-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;

    .plan-header {
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

    .plan-info {
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

    .plan-footer {
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
