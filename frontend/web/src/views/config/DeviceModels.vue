<template>
  <div class="device-models-container">
    <van-nav-bar title="设备型号" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="`${item.manufacturer} | ${item.typeName}`"
            is-link
            @click="handleEdit(item)"
          />
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
  { id: 1, name: 'E-2000', manufacturer: '上海电梯', typeName: '电梯' },
  { id: 2, name: 'K-5000', manufacturer: '格力空调', typeName: '空调' },
  { id: 3, name: 'DS-2CD3T86', manufacturer: '海康威视', typeName: '安防' }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.device-models-container {
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
