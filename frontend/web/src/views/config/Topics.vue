<template>
  <div class="topics-container">
    <van-nav-bar title="专题管理" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="`${item.deviceCount}台设备`"
            is-link
            @click="handleEdit(item)"
          >
            <template #icon>
              <van-icon :name="item.icon || 'folder-o'" size="20" style="margin-right: 12px; color: #1890ff;" />
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
  { id: 1, name: '住宅小区', deviceCount: 156, icon: 'home-o' },
  { id: 2, name: '商业楼宇', deviceCount: 89, icon: 'shop-o' },
  { id: 3, name: '工业厂房', deviceCount: 234, icon: 'cluster-o' }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.topics-container {
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
