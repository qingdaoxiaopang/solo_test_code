<template>
  <div class="layout-sidebar">
    <div class="sidebar-header">
      <div class="logo">
        <h1>设备运维平台</h1>
      </div>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      router
    >
      <el-menu-item index="/dashboard">
        <el-icon><HomeFilled /></el-icon>
        <template #title>首页</template>
      </el-menu-item>
      
      <el-sub-menu index="/project">
        <template #title>
          <el-icon><Location /></el-icon>
          <span>项目管理</span>
        </template>
        <el-menu-item index="/project/list">项目列表</el-menu-item>
        <el-menu-item index="/project/form">添加项目</el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="/device">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>设备管理</span>
        </template>
        <el-menu-item index="/device/list">设备列表</el-menu-item>
        <el-menu-item index="/device/inspection-plans">巡检计划</el-menu-item>
        <el-menu-item index="/device/maintenance-orders">维护工单</el-menu-item>
        <el-menu-item index="/device/faults">故障管理</el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="/config">
        <template #title>
          <el-icon><Menu /></el-icon>
          <span>业务配置</span>
        </template>
        <el-menu-item index="/config/device-types">设备类型</el-menu-item>
        <el-menu-item index="/config/manufacturers">设备厂商</el-menu-item>
        <el-menu-item index="/config/device-models">设备型号</el-menu-item>
        <el-menu-item index="/config/topics">专题管理</el-menu-item>
        <el-menu-item index="/config/spare-parts">备件库</el-menu-item>
        <el-menu-item index="/config/maintenance-companies">维护公司</el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="/system">
        <template #title>
          <el-icon><User /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/system/users">用户管理</el-menu-item>
        <el-menu-item index="/system/roles">角色管理</el-menu-item>
        <el-menu-item index="/system/permissions">权限管理</el-menu-item>
        <el-menu-item index="/system/dicts">字典管理</el-menu-item>
        <el-menu-item index="/system/logs">日志管理</el-menu-item>
        <el-menu-item index="/system/settings">系统配置</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { HomeFilled, Location, Setting, Menu, User } from '@element-plus/icons-vue'

const route = useRoute()
const appStore = useAppStore()
const isCollapse = computed(() => !appStore.sidebarOpened)
const activeMenu = computed(() => route.path)
</script>

<style lang="scss" scoped>
.layout-sidebar {
  height: 100%;
  background: #304156;

  .sidebar-header {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #2b3a4a;

    .logo {
      h1 {
        font-size: 18px;
        font-weight: 600;
        color: #fff;
        margin: 0;
      }
    }
  }

  :deep(.el-menu) {
    border: none;

    .el-menu-item,
    .el-sub-menu__title {
      &:hover {
        background: #263445 !important;
      }
    }

    .el-menu-item.is-active {
      background: #263445 !important;
    }
  }
}
</style>
