<template>
  <div class="settings-container">
    <van-nav-bar :title="isMine ? '个人中心' : '系统配置'" left-arrow @click-left="handleBack" />

    <van-cell-group inset>
      <van-cell title="修改密码" is-link @click="handleChangePassword" />
      <van-cell title="通知设置" is-link @click="handleNotification" />
      <van-cell title="清除缓存" is-link @click="handleClearCache" />
    </van-cell-group>

    <van-cell-group inset style="margin-top: 16px;" v-if="isMine">
      <van-cell title="关于我们" is-link @click="handleAbout" />
      <van-cell title="版本信息" value="v1.0.0" />
    </van-cell-group>

    <div class="logout-button" v-if="isMine">
      <van-button block type="danger" @click="handleLogout">退出登录</van-button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Dialog, Toast } from 'vant'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isMine = computed(() => route.path === '/system/mine')

const handleBack = () => router.back()
const handleChangePassword = () => {}
const handleNotification = () => {}
const handleClearCache = () => {
  Dialog.confirm({
    title: '清除缓存',
    message: '确定要清除缓存吗？'
  }).then(() => {
    Toast.success('缓存已清除')
  })
}

const handleAbout = () => {}
const handleLogout = () => {
  Dialog.confirm({
    title: '退出登录',
    message: '确定要退出登录吗？'
  }).then(() => {
    userStore.logout()
  })
}
</script>

<style lang="scss" scoped>
.settings-container {
  min-height: 100vh;
  background: #f7f8fa;

  .logout-button {
    padding: 32px 16px;
  }
}
</style>
