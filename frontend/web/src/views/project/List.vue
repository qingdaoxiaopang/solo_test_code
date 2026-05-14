<template>
  <div class="project-list-container">
    <van-search
      v-model="searchQuery"
      placeholder="搜索项目名称"
      @search="handleSearch"
    />
    
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="project-list">
          <div
            v-for="project in projectList"
            :key="project.id"
            class="project-card"
            @click="goToDetail(project)"
          >
            <div class="project-header">
              <h3 class="project-name">{{ project.name }}</h3>
              <van-tag :type="getStatusType(project.status)">
                {{ getStatusText(project.status) }}
              </van-tag>
            </div>
            <div class="project-info">
              <div class="info-item">
                <van-icon name="location-o" />
                <span>{{ project.location || '未设置地址' }}</span>
              </div>
              <div class="info-item">
                <van-icon name="user-o" />
                <span>{{ project.managerName || '未分配负责人' }}</span>
              </div>
            </div>
            <div class="project-stats">
              <div class="stat-item">
                <span class="stat-value">{{ project.deviceCount || 0 }}</span>
                <span class="stat-label">设备</span>
              </div>
              <div class="stat-item">
                <span class="stat-value">{{ project.userCount || 0 }}</span>
                <span class="stat-label">成员</span>
              </div>
              <div class="stat-item">
                <span class="stat-value">{{ project.faultCount || 0 }}</span>
                <span class="stat-label">故障</span>
              </div>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

    <div class="add-button" v-if="hasPermission('project:create')">
      <van-button round type="primary" icon="plus" @click="goToForm" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getProjectList } from '@/api/projects'
import { Toast } from 'vant'

const router = useRouter()
const userStore = useUserStore()

const searchQuery = ref('')
const refreshing = ref(false)
const loading = ref(false)
const finished = ref(false)
const projectList = ref([])
const page = ref(1)
const pageSize = ref(10)

const hasPermission = (permission) => {
  return userStore.hasPermission(permission)
}

const getStatusType = (status) => {
  const map = { active: 'success', pending: 'warning', completed: 'info', suspended: 'danger' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { active: '进行中', pending: '待启动', completed: '已完成', suspended: '已暂停' }
  return map[status] || status
}

const onLoad = async () => {
  try {
    const res = await getProjectList({
      page: page.value,
      pageSize: pageSize.value,
      keyword: searchQuery.value
    })
    const data = res.data || res
    projectList.value = [...projectList.value, ...data.list || data]
    page.value++
    if (page.value > (data.totalPage || 1)) {
      finished.value = true
    }
  } catch (error) {
    console.error('Load projects error:', error)
  } finally {
    loading.value = false
  }
}

const onRefresh = async () => {
  page.value = 1
  finished.value = false
  projectList.value = []
  await onLoad()
  refreshing.value = false
}

const handleSearch = () => {
  onRefresh()
}

const goToDetail = (project) => {
  router.push(`/project/detail/${project.id}`)
}

const goToForm = () => {
  router.push('/project/form')
}

onMounted(() => {
  onRefresh()
})
</script>

<style lang="scss" scoped>
.project-list-container {
  min-height: 100%;
  background: #f7f8fa;
  padding-bottom: 80px;

  .project-list {
    padding: 16px;
  }

  .project-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;
    cursor: pointer;
    transition: all 0.3s;

    &:active {
      transform: scale(0.98);
    }

    .project-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 12px;

      .project-name {
        font-size: 16px;
        font-weight: 600;
        color: #333;
      }
    }

    .project-info {
      display: flex;
      flex-direction: column;
      gap: 8px;
      margin-bottom: 16px;

      .info-item {
        display: flex;
        align-items: center;
        gap: 6px;
        font-size: 13px;
        color: #666;

        :deep(.van-icon) {
          color: #999;
        }
      }
    }

    .project-stats {
      display: flex;
      justify-content: space-around;
      padding-top: 16px;
      border-top: 1px solid #f5f5f5;

      .stat-item {
        display: flex;
        flex-direction: column;
        align-items: center;

        .stat-value {
          font-size: 18px;
          font-weight: 600;
          color: #333;
        }

        .stat-label {
          font-size: 12px;
          color: #999;
          margin-top: 4px;
        }
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
