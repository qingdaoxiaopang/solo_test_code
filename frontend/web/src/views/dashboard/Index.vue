<template>
  <div class="dashboard-container">
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <div class="dashboard-content">
        <div class="dashboard-header">
          <div class="welcome-section">
            <h2>欢迎回来，{{ userStore.nickname }}</h2>
            <p>{{ currentDate }}</p>
          </div>
          <van-button size="small" type="primary" @click="handleRefresh">
            <van-icon name="replay" /> 刷新
          </van-button>
        </div>

        <div class="stats-grid">
          <div class="stat-card" @click="goToDevice">
            <div class="stat-icon" style="background: #e6f7ff;">
              <van-icon name="apps-o" size="24" color="#1890ff" />
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ stats.deviceTotal || 0 }}</span>
              <span class="stat-label">设备总数</span>
            </div>
          </div>

          <div class="stat-card" @click="goToFaults">
            <div class="stat-icon" style="background: #fff2e8;">
              <van-icon name="warning-o" size="24" color="#fa8c16" />
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ stats.faultTotal || 0 }}</span>
              <span class="stat-label">故障告警</span>
            </div>
          </div>

          <div class="stat-card" @click="goToMaintenance">
            <div class="stat-icon" style="background: #f6ffed;">
              <van-icon name="setting-o" size="24" color="#52c41a" />
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ stats.maintenanceTotal || 0 }}</span>
              <span class="stat-label">维护工单</span>
            </div>
          </div>

          <div class="stat-card" @click="goToInspection">
            <div class="stat-icon" style="background: #fff1f0;">
              <van-icon name="todo-list-o" size="24" color="#ff4d4f" />
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ stats.inspectionTotal || 0 }}</span>
              <span class="stat-label">巡检任务</span>
            </div>
          </div>
        </div>

        <div class="section">
          <div class="section-header">
            <h3>设备状态分布</h3>
          </div>
          <div class="device-status-chart">
            <div class="chart-legend">
              <div class="legend-item">
                <span class="legend-dot" style="background: #52c41a;"></span>
                <span class="legend-label">在线</span>
                <span class="legend-value">{{ deviceStatus.online || 0 }}</span>
              </div>
              <div class="legend-item">
                <span class="legend-dot" style="background: #d9d9d9;"></span>
                <span class="legend-label">离线</span>
                <span class="legend-value">{{ deviceStatus.offline || 0 }}</span>
              </div>
              <div class="legend-item">
                <span class="legend-dot" style="background: #ff4d4f;"></span>
                <span class="legend-label">故障</span>
                <span class="legend-value">{{ deviceStatus.fault || 0 }}</span>
              </div>
              <div class="legend-item">
                <span class="legend-dot" style="background: #fa8c16;"></span>
                <span class="legend-label">维护中</span>
                <span class="legend-value">{{ deviceStatus.maintenance || 0 }}</span>
              </div>
            </div>
            <div class="chart-bars">
              <div
                v-for="item in statusChartData"
                :key="item.name"
                class="chart-bar-item"
              >
                <div class="bar-wrapper">
                  <div
                    class="bar-fill"
                    :style="{
                      height: `${item.percent}%`,
                      background: item.color
                    }"
                  ></div>
                </div>
                <span class="bar-label">{{ item.name }}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="section">
          <div class="section-header">
            <h3>待处理任务</h3>
          </div>
          <div class="task-list">
            <van-cell-group>
              <van-cell
                v-for="task in pendingTasks"
                :key="task.id"
                :title="task.title"
                :label="task.description"
                :value="task.time"
                is-link
                @click="handleTaskClick(task)"
              >
                <template #icon>
                  <van-icon
                    :name="task.icon"
                    :color="task.color"
                    size="20"
                    style="margin-right: 12px;"
                  />
                </template>
                <template #value>
                  <van-tag :type="task.tagType">{{ task.status }}</van-tag>
                </template>
              </van-cell>
            </van-cell-group>
          </div>
        </div>

        <div class="section">
          <div class="section-header">
            <h3>快捷操作</h3>
          </div>
          <div class="quick-actions">
            <div class="action-item" @click="goToAddDevice">
              <van-icon name="plus" size="24" />
              <span>添加设备</span>
            </div>
            <div class="action-item" @click="goToReportFault">
              <van-icon name="warning" size="24" />
              <span>故障上报</span>
            </div>
            <div class="action-item" @click="goToCreateOrder">
              <van-icon name="orders-o" size="24" />
              <span>创建工单</span>
            </div>
            <div class="action-item" @click="goToScanInspection">
              <van-icon name="scan" size="24" />
              <span>扫码巡检</span>
            </div>
          </div>
        </div>
      </div>
    </van-pull-refresh>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useProjectStore } from '@/stores/project'
import { useAppStore } from '@/stores/app'
import dayjs from 'dayjs'

const router = useRouter()
const userStore = useUserStore()
const projectStore = useProjectStore()
const appStore = useAppStore()

const refreshing = ref(false)

const currentDate = computed(() => {
  return dayjs().format('YYYY年MM月DD日 dddd')
})

const stats = ref({
  deviceTotal: 156,
  faultTotal: 8,
  maintenanceTotal: 23,
  inspectionTotal: 12
})

const deviceStatus = ref({
  online: 120,
  offline: 20,
  fault: 8,
  maintenance: 8
})

const statusChartData = computed(() => {
  const total = Object.values(deviceStatus.value).reduce((a, b) => a + b, 0) || 1
  const colors = {
    '在线': '#52c41a',
    '离线': '#d9d9d9',
    '故障': '#ff4d4f',
    '维护中': '#fa8c16'
  }
  return Object.entries(deviceStatus.value).map(([key, value]) => ({
    name: key === 'online' ? '在线' : key === 'offline' ? '离线' : key === 'fault' ? '故障' : '维护中',
    value,
    percent: Math.round((value / total) * 100),
    color: colors[key === 'online' ? '在线' : key === 'offline' ? '离线' : key === 'fault' ? '故障' : '维护中']
  }))
})

const pendingTasks = ref([
  {
    id: 1,
    title: '3号电梯定期巡检',
    description: 'B栋3号电梯日常巡检任务',
    time: '2024-01-15 09:00',
    status: '待巡检',
    tagType: 'warning',
    icon: 'todo-list-o',
    color: '#fa8c16',
    type: 'inspection'
  },
  {
    id: 2,
    title: '中央空调故障报修',
    description: 'A栋5层中央空调无法启动',
    time: '2024-01-14 16:30',
    status: '处理中',
    tagType: 'primary',
    icon: 'warning-o',
    color: '#1890ff',
    type: 'fault'
  },
  {
    id: 3,
    title: '消防设备维护',
    description: '月度消防设备检查维护',
    time: '2024-01-16 10:00',
    status: '待派单',
    tagType: 'info',
    icon: 'setting-o',
    color: '#52c41a',
    type: 'maintenance'
  }
])

const onRefresh = async () => {
  await loadData()
  refreshing.value = false
}

const loadData = async () => {
}

const handleRefresh = () => {
  onRefresh()
}

const goToDevice = () => {
  router.push('/device/list')
}

const goToFaults = () => {
  router.push('/device/faults')
}

const goToMaintenance = () => {
  router.push('/device/maintenance-orders')
}

const goToInspection = () => {
  router.push('/device/inspection-plans')
}

const goToAddDevice = () => {
  router.push('/device/form')
}

const goToReportFault = () => {
  router.push('/device/faults?action=report')
}

const goToCreateOrder = () => {
  router.push('/device/maintenance-orders?action=create')
}

const goToScanInspection = () => {
}

const handleTaskClick = (task) => {
  if (task.type === 'inspection') {
    router.push('/device/inspection-plans')
  } else if (task.type === 'fault') {
    router.push('/device/faults')
  } else if (task.type === 'maintenance') {
    router.push('/device/maintenance-orders')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  min-height: 100%;
  background: #f7f8fa;

  .dashboard-content {
    padding: 16px;
  }

  .dashboard-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .welcome-section {
      h2 {
        font-size: 20px;
        font-weight: 600;
        color: #333;
        margin-bottom: 4px;
      }

      p {
        font-size: 13px;
        color: #999;
      }
    }
  }

  .stats-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 12px;
    margin-bottom: 20px;

    .stat-card {
      background: #fff;
      border-radius: 12px;
      padding: 16px;
      display: flex;
      align-items: center;
      gap: 12px;
      cursor: pointer;
      transition: all 0.3s;

      &:active {
        transform: scale(0.98);
      }

      .stat-icon {
        width: 48px;
        height: 48px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .stat-info {
        display: flex;
        flex-direction: column;

        .stat-value {
          font-size: 24px;
          font-weight: 600;
          color: #333;
        }

        .stat-label {
          font-size: 12px;
          color: #999;
        }
      }
    }
  }

  .section {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 16px;

    .section-header {
      margin-bottom: 16px;

      h3 {
        font-size: 16px;
        font-weight: 600;
        color: #333;
      }
    }
  }

  .device-status-chart {
    .chart-legend {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      gap: 12px;
      margin-bottom: 20px;

      .legend-item {
        display: flex;
        align-items: center;
        gap: 6px;

        .legend-dot {
          width: 8px;
          height: 8px;
          border-radius: 50%;
        }

        .legend-label {
          font-size: 13px;
          color: #666;
        }

        .legend-value {
          font-size: 13px;
          font-weight: 600;
          color: #333;
        }
      }
    }

    .chart-bars {
      display: flex;
      justify-content: space-around;
      align-items: flex-end;
      height: 100px;

      .chart-bar-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 8px;

        .bar-wrapper {
          width: 40px;
          height: 80px;
          background: #f5f5f5;
          border-radius: 4px;
          display: flex;
          align-items: flex-end;
          overflow: hidden;

          .bar-fill {
            width: 100%;
            border-radius: 4px;
            transition: height 0.5s ease;
          }
        }

        .bar-label {
          font-size: 12px;
          color: #666;
        }
      }
    }
  }

  .task-list {
    :deep(.van-cell-group) {
      margin: 0 -16px;
      background: transparent;

      .van-cell {
        padding: 12px 16px;
        background: #fff;
        margin-bottom: 8px;
        border-radius: 8px;

        &::after {
          display: none;
        }
      }
    }
  }

  .quick-actions {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;

    .action-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 8px;
      padding: 16px 8px;
      background: #f7f8fa;
      border-radius: 8px;
      cursor: pointer;
      transition: all 0.3s;

      &:active {
        background: #eee;
      }

      span {
        font-size: 12px;
        color: #666;
      }
    }
  }
}
</style>
