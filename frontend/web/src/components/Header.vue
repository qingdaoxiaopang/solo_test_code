<template>
  <div class="layout-header">
    <div class="header-left">
      <el-button text @click="toggleSidebar">
        <el-icon size="20"><Fold v-if="!sidebarOpened" /><Expand v-else /></el-icon>
      </el-button>
    </div>

    <div class="header-center">
      <el-dropdown @command="handleProjectCommand">
        <span class="project-selector">
          {{ currentProjectName || '请选择项目' }}
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item
              v-for="project in projects"
              :key="project.id"
              :command="project.id"
              :disabled="project.id === currentProjectId"
            >
              {{ project.name }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <div class="header-right">
      <el-dropdown @command="handleUserCommand">
        <span class="user-info">
          <el-avatar :size="32" :src="avatar">
            {{ nickname?.charAt(0) }}
          </el-avatar>
          <span class="username">{{ nickname }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
            <el-dropdown-item command="settings">系统设置</el-dropdown-item>
            <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useProjectStore } from '@/stores/project'
import { useAppStore } from '@/stores/app'
import { Fold, Expand, ArrowDown } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const projectStore = useProjectStore()
const appStore = useAppStore()

const sidebarOpened = computed(() => appStore.sidebarOpened)
const projects = computed(() => projectStore.projects)
const currentProjectId = computed(() => projectStore.currentProjectId)
const currentProjectName = computed(() => projectStore.currentProjectName)
const nickname = computed(() => userStore.nickname)
const avatar = computed(() => userStore.avatar)

const toggleSidebar = () => {
  appStore.toggleSidebar()
}

const handleProjectCommand = async (projectId) => {
  try {
    await projectStore.switchProject(projectId)
  } catch (error) {
    console.error('Switch project error:', error)
  }
}

const handleUserCommand = (command) => {
  switch (command) {
    case 'profile':
      router.push('/system/mine')
      break
    case 'settings':
      router.push('/system/settings')
      break
    case 'logout':
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userStore.logout()
      })
      break
  }
}
</script>

<style lang="scss" scoped>
.layout-header {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: #fff;

  .header-left {
    display: flex;
    align-items: center;
  }

  .header-center {
    .project-selector {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      padding: 8px 12px;
      border-radius: 4px;
      transition: background 0.3s;

      &:hover {
        background: #f5f5f5;
      }
    }
  }

  .header-right {
    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      padding: 4px 8px;
      border-radius: 4px;
      transition: background 0.3s;

      &:hover {
        background: #f5f5f5;
      }

      .username {
        font-size: 14px;
        color: #333;
      }
    }
  }
}
</style>
