<template>
  <div class="device-types-container">
    <van-nav-bar title="设备类型" left-arrow @click-left="handleBack" />

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
              <van-tag :type="item.enabled ? 'success' : 'info'">
                {{ item.enabled ? '启用' : '禁用' }}
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
  { id: 1, name: '电梯', description: '各类电梯设备', enabled: true },
  { id: 2, name: '空调', description: '中央空调及分体空调', enabled: true },
  { id: 3, name: '消防', description: '消防报警及灭火设备', enabled: true },
  { id: 4, name: '安防', description: '监控及门禁设备', enabled: true }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.device-types-container {
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
