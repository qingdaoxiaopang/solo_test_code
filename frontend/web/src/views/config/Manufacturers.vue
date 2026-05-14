<template>
  <div class="manufacturers-container">
    <van-nav-bar title="设备厂商" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <van-cell-group inset>
          <van-cell
            v-for="item in list"
            :key="item.id"
            :title="item.name"
            :label="item.contact"
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
  { id: 1, name: '上海电梯有限公司', contact: '张经理 021-12345678' },
  { id: 2, name: '格力空调集团', contact: '李经理 0756-8765432' },
  { id: 3, name: '海康威视', contact: '王经理 0571-9876543' }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleEdit = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.manufacturers-container {
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
