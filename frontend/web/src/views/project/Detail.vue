<template>
  <div class="project-detail-container">
    <van-nav-bar
      title="项目详情"
      left-text="返回"
      right-text="编辑"
      left-arrow
      @click-left="handleBack"
      @click-right="handleEdit"
    />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <div class="detail-content" v-if="project">
        <div class="detail-header">
          <h2>{{ project.name }}</h2>
          <van-tag :type="getStatusType(project.status)" size="large">
            {{ getStatusText(project.status) }}
          </van-tag>
        </div>

        <div class="detail-section">
          <h3>基本信息</h3>
          <van-cell-group inset>
            <van-cell title="项目编号" :value="project.code || '-'" />
            <van-cell title="项目地址" :value="project.location || '-'" />
            <van-cell title="项目负责人" :value="project.managerName || '-'" />
            <van-cell title="联系电话" :value="project.managerPhone || '-'" />
            <van-cell title="创建时间" :value="formatDate(project.createTime)" />
            <van-cell title="项目描述" :value="project.description || '-'" />
          </van-cell-group>
        </div>

        <div class="detail-section">
          <h3>设备统计</h3>
          <div class="stats-grid">
            <div class="stat-card">
              <van-icon name="apps-o" size="24" color="#1890ff" />
              <span class="stat-value">{{ project.deviceCount || 0 }}</span>
              <span class="stat-label">设备总数</span>
            </div>
            <div class="stat-card">
              <van-icon name="chart-trending-o" size="24" color="#52c41a" />
              <span class="stat-value">{{ project.onlineCount || 0 }}</span>
              <span class="stat-label">在线设备</span>
            </div>
            <div class="stat-card">
              <van-icon name="warning-o" size="24" color="#ff4d4f" />
              <span class="stat-value">{{ project.faultCount || 0 }}</span>
              <span class="stat-label">故障设备</span>
            </div>
          </div>
        </div>

        <div class="detail-section">
          <h3>设备列表</h3>
          <van-cell-group inset>
            <van-cell
              v-for="device in devices"
              :key="device.id"
              :title="device.name"
              :label="device.modelName"
              is-link
              @click="goToDevice(device)"
            >
              <template #value>
                <van-tag :type="getDeviceStatusType(device.status)">
                  {{ getDeviceStatusText(device.status) }}
                </van-tag>
              </template>
            </van-cell>
          </van-cell-group>
          <div class="more-link" v-if="devices.length > 0" @click="goToDeviceList">
            查看全部设备 <van-icon name="arrow" />
          </div>
        </div>

        <div class="detail-section">
          <h3>项目成员</h3>
          <van-cell-group inset>
            <van-cell
              v-for="member in members"
              :key="member.id"
              :title="member.name"
              :label="member.role"
              is-link
              @click="goToUser(member)"
            >
              <template #icon>
                <van-image
                  round
                  width="36"
                  height="36"
                  :src="member.avatar"
                  style="margin-right: 12px;"
                >
                  <template #loading>
                    <van-icon name="user-o" size="20" />
                  </template>
                </van-image>
              </template>
            </van-cell>
          </van-cell-group>
        </div>
      </div>

      <van-empty v-else description="项目不存在" />
    </van-pull-refresh>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProjectById, getProjectDevices } from '@/api/projects'
import { formatDate } from '@/utils/format'

const route = useRoute()
const router = useRouter()
const projectId = computed(() => route.params.id)

const refreshing = ref(false)
const project = ref(null)
const devices = ref([])
const members = ref([])

const getStatusType = (status) => {
  const map = { active: 'success', pending: 'warning', completed: 'info', suspended: 'danger' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { active: '进行中', pending: '待启动', completed: '已完成', suspended: '已暂停' }
  return map[status] || status
}

const getDeviceStatusType = (status) => {
  const map = { online: 'success', offline: 'info', fault: 'danger', maintenance: 'warning' }
  return map[status] || 'info'
}

const getDeviceStatusText = (status) => {
  const map = { online: '在线', offline: '离线', fault: '故障', maintenance: '维护中' }
  return map[status] || status
}

const loadData = async () => {
  try {
    const res = await getProjectById(projectId.value)
    project.value = res.data || res
    const deviceRes = await getProjectDevices(projectId.value, { pageSize: 5 })
    devices.value = deviceRes.data?.list || deviceRes.list || []
    members.value = project.value.members || []
  } catch (error) {
    console.error('Load project error:', error)
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
  router.push(`/project/form?id=${projectId.value}`)
}

const goToDevice = (device) => {
  router.push(`/device/detail/${device.id}`)
}

const goToDeviceList = () => {
  router.push('/device/list')
}

const goToUser = (member) => {
}

onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.project-detail-container {
  min-height: 100%;
  background: #f7f8fa;

  .detail-content {
    padding: 16px;

    .detail-header {
      background: #fff;
      border-radius: 12px;
      padding: 20px;
      margin-bottom: 16px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      h2 {
        font-size: 20px;
        font-weight: 600;
        color: #333;
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

      .stats-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 12px;

        .stat-card {
          display: flex;
          flex-direction: column;
          align-items: center;
          padding: 16px;
          background: #f7f8fa;
          border-radius: 8px;

          .stat-value {
            font-size: 24px;
            font-weight: 600;
            color: #333;
            margin: 8px 0 4px;
          }

          .stat-label {
            font-size: 12px;
            color: #666;
          }
        }
      }

      .more-link {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 12px;
        color: #1890ff;
        font-size: 14px;
      }
    }
  }
}
</style>
