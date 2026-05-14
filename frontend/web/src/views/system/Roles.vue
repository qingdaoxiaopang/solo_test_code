<template>
  <div class="roles-container">
    <van-nav-bar title="角色管理" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="item.description"
            is-link
            @click="handleEdit(item)"
          >
            <template #value>
              <span>{{ item.userCount }}人</span>
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
  { id: 1, name: '超级管理员', description: '拥有系统所有权限', userCount: 2 },
  { id: 2, name: '运维主管', description: '管理运维人员', userCount: 5 },
  { id: 3, name: '运维人员', description: '执行运维任务', userCount: 20 }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.roles-container {
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
