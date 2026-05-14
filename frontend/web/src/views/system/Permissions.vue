<template>
  <div class="permissions-container">
    <van-nav-bar title="权限管理" left-arrow @click-left="handleBack" />

    <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="permissionItems"
      height="calc(100vh - 46px)"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeIds = ref([1, 2])
const activeIndex = ref(0)

const permissionItems = ref([
  {
    text: '设备管理',
    children: [
      { text: '设备列表', id: 1 },
      { text: '添加设备', id: 2 },
      { text: '编辑设备', id: 3 },
      { text: '删除设备', id: 4 }
    ]
  },
  {
    text: '项目管理',
    children: [
      { text: '项目列表', id: 5 },
      { text: '添加项目', id: 6 },
      { text: '编辑项目', id: 7 }
    ]
  },
  {
    text: '系统管理',
    children: [
      { text: '用户管理', id: 8 },
      { text: '角色管理', id: 9 },
      { text: '权限管理', id: 10 }
    ]
  }
])

const handleBack = () => router.back()
</script>

<style lang="scss" scoped>
.permissions-container {
  min-height: 100vh;
  background: #f7f8fa;
}
</style>
