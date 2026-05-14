<template>
  <div class="logs-container">
    <van-nav-bar title="日志管理" left-arrow @click-left="handleBack" />

    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="操作日志" name="operation"></van-tab>
      <van-tab title="登录日志" name="login"></van-tab>
      <van-tab title="错误日志" name="error"></van-tab>
    </van-tabs>

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.title"
            :label="item.description"
          >
            <template #value>
              <div class="log-info">
                <span>{{ item.username }}</span>
                <span>{{ formatDateTime(item.createTime) }}</span>
              </div>
            </template>
          </van-cell>
        </van-cell-group>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { formatDateTime } from '@/utils/format'

const router = useRouter()
const activeTab = ref('operation')
const refreshing = ref(false)
const finished = ref(true)
const list = ref([
  { id: 1, title: '用户登录', description: '登录系统', username: 'admin', createTime: '2024-01-15 10:30:00' },
  { id: 2, title: '创建设备', description: '新增电梯设备', username: 'admin', createTime: '2024-01-15 11:20:00' },
  { id: 3, title: '编辑项目', description: '修改项目信息', username: 'admin', createTime: '2024-01-15 14:00:00' }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
</script>

<style lang="scss" scoped>
.logs-container {
  min-height: 100vh;
  background: #f7f8fa;

  .log-info {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    font-size: 12px;
    color: #999;
  }
}
</style>
