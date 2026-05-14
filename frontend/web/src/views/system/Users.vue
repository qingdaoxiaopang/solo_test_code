<template>
  <div class="users-container">
    <van-nav-bar title="用户管理" left-arrow @click-left="handleBack">
      <template #right>
        <van-icon name="search" size="18" @click="handleSearch" />
      </template>
    </van-nav-bar>

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            is-link
            @click="handleDetail(item)"
          >
            <template #icon>
              <van-image round width="40" height="40" :src="item.avatar" style="margin-right: 12px;">
                <template #loading>
                  <van-icon name="user-o" size="20" />
                </template>
              </van-image>
            </template>
            <template #title>
              <div>{{ item.username }}</div>
            </template>
            <template #label>
              <div>{{ item.nickname }} | {{ item.department }}</div>
            </template>
            <template #value>
              <van-tag :type="item.status === 'active' ? 'success' : 'info'">
                {{ item.status === 'active' ? '启用' : '禁用' }}
              </van-tag>
            </template>
          </van-cell>
        </van-cell-group>
      </van-list>
    </van-pull-refresh>

    <div class="add-button">
      <van-button round type="primary" icon="plus" @click="handleAdd" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const refreshing = ref(false)
const finished = ref(true)
const list = ref([
  { id: 1, username: 'admin', nickname: '管理员', department: '系统管理部', status: 'active', avatar: '' },
  { id: 2, username: 'user01', nickname: '张三', department: '运维部', status: 'active', avatar: '' },
  { id: 3, username: 'user02', nickname: '李四', department: '技术部', status: 'active', avatar: '' }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleSearch = () => {}
const handleDetail = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.users-container {
  min-height: 100vh;
  background: #f7f8fa;
  padding-bottom: 80px;

  .add-button {
    position: fixed;
    bottom: 70px;
    right: 16px;
    z-index: 100;
  }
}
</style>
