<template>
  <div class="maintenance-companies-container">
    <van-nav-bar title="维护公司" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="item.serviceScope"
            is-link
            @click="handleEdit(item)"
          >
            <template #value>
              <van-tag :type="item.enabled ? 'success' : 'info'">
                {{ item.enabled ? '合作中' : '已停止' }}
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
  { id: 1, name: '电梯维保公司A', serviceScope: '电梯维修保养', enabled: true },
  { id: 2, name: '空调维保公司B', serviceScope: '空调维修保养', enabled: true },
  { id: 3, name: '消防维保公司C', serviceScope: '消防设备维保', enabled: false }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.maintenance-companies-container {
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
