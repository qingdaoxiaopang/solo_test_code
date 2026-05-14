<template>
  <div class="dicts-container">
    <van-nav-bar title="字典管理" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="`${item.code} | ${item.itemCount}项`"
            is-link
            @click="handleDetail(item)"
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
  { id: 1, name: '设备状态', code: 'device_status', itemCount: 4 },
  { id: 2, name: '故障等级', code: 'fault_level', itemCount: 3 },
  { id: 3, name: '工单状态', code: 'order_status', itemCount: 5 }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleDetail = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.dicts-container {
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
