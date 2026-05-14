<template>
  <div class="app-container" :class="{ 'is-mobile': isMobile }">
    <template v-if="isMobile">
      <van-nav-bar
        v-if="showNavBar"
        :title="pageTitle"
        left-arrow
        @click-left="handleBack"
        @click-right="handleMore"
      >
        <template #right>
          <van-icon name="ellipsis" size="18" />
        </template>
      </van-nav-bar>
      <div class="mobile-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
      <van-tabbar
        v-if="showTabBar"
        v-model="activeTab"
        @change="handleTabChange"
      >
        <van-tabbar-item icon-prefix="van-icon" name="home">
          <template #icon>
            <van-icon name="wap-home" />
          </template>
          首页
        </van-tabbar-item>
        <van-tabbar-item icon-prefix="van-icon" name="project">
          <template #icon>
            <van-icon name="cluster-o" />
          </template>
          项目
        </van-tabbar-item>
        <van-tabbar-item icon-prefix="van-icon" name="device">
          <template #icon>
            <van-icon name="apps-o" />
          </template>
          设备
        </van-tabbar-item>
        <van-tabbar-item icon-prefix="van-icon" name="mine">
          <template #icon>
            <van-icon name="user-o" />
          </template>
          我的
        </van-tabbar-item>
      </van-tabbar>
    </template>
    <template v-else>
      <el-container class="pc-container">
        <el-aside width="200px" class="pc-sidebar">
          <layout-sidebar />
        </el-aside>
        <el-container>
          <el-header height="60px" class="pc-header">
            <layout-header />
          </el-header>
          <el-main class="pc-main">
            <router-view v-slot="{ Component }">
              <transition name="fade" mode="out-in">
                <component :is="Component" />
              </transition>
            </router-view>
          </el-main>
        </el-container>
      </el-container>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import LayoutSidebar from '@/components/Sidebar.vue'
import LayoutHeader from '@/components/Header.vue'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()

const isMobile = computed(() => appStore.isMobile)
const activeTab = ref('home')
const pageTitle = computed(() => route.meta?.title || '设备运维平台')

const showNavBar = computed(() => {
  return route.meta?.showNavBar !== false && !['login'].includes(route.name)
})

const showTabBar = computed(() => {
  return route.meta?.showTabBar !== false && !['login'].includes(route.name)
})

watch(() => route.path, (path) => {
  if (path.startsWith('/dashboard') || path === '/') activeTab.value = 'home'
  else if (path.startsWith('/project')) activeTab.value = 'project'
  else if (path.startsWith('/device')) activeTab.value = 'device'
  else if (path.startsWith('/system/mine')) activeTab.value = 'mine'
}, { immediate: true })

const handleBack = () => {
  if (window.history.length > 1) {
    router.back()
  } else {
    router.push('/dashboard')
  }
}

const handleMore = () => {
}

const handleTabChange = (index) => {
  const routes = {
    home: '/dashboard',
    project: '/project',
    device: '/device',
    mine: '/system/mine'
  }
  router.push(routes[index] || '/dashboard')
}

onMounted(() => {
  appStore.initDevice()
  window.addEventListener('resize', appStore.initDevice)
})
</script>

<style lang="scss">
.app-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;

  &.is-mobile {
    display: flex;
    flex-direction: column;
    background: #f7f8fa;

    .mobile-content {
      flex: 1;
      overflow-y: auto;
      -webkit-overflow-scrolling: touch;
    }
  }

  .pc-container {
    height: 100%;
  }

  .pc-sidebar {
    background: #304156;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .pc-header {
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    display: flex;
    align-items: center;
    padding: 0 20px;
  }

  .pc-main {
    background: #f0f2f5;
    padding: 20px;
    overflow-y: auto;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
