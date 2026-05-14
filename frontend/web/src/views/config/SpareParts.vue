<template>
  <div class="spare-parts-container">
    <van-nav-bar title="备件库" left-arrow @click-left="handleBack" />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list :finished="finished">
        <div class="part-list">
          <div v-for="item in list" :key="item.id" class="part-card" @click="handleDetail(item)">
            <div class="part-info">
              <h3>{{ item.name }}</h3>
              <p>{{ item.model }}</p>
            </div>
            <div class="part-stock">
              <span class="stock-label">库存</span>
              <span class="stock-value" :class="{ low: item.stock <= item.minStock }">{{ item.stock }}</span>
            </div>
          </div>
        </div>
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
  { id: 1, name: '电梯曳引轮', model: 'E-2000', stock: 5, minStock: 3 },
  { id: 2, name: '空调压缩机', model: 'K-5000', stock: 2, minStock: 2 },
  { id: 3, name: '消防感温探头', model: 'JTY-GD', stock: 20, minStock: 10 }
])

const handleBack = () => router.back()
const onRefresh = () => { refreshing.value = false }
const handleDetail = (item) => {}
const handleAdd = () => {}
</script>

<style lang="scss" scoped>
.spare-parts-container {
  min-height: 100vh;
  background: #f7f8fa;
  padding-bottom: 80px;

  .part-list {
    padding: 16px;
  }

  .part-card {
    background: #fff;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .part-info {
      h3 {
        font-size: 16px;
        font-weight: 600;
        color: #333;
        margin-bottom: 4px;
      }

      p {
        font-size: 13px;
        color: #999;
      }
    }

    .part-stock {
      text-align: center;

      .stock-label {
        display: block;
        font-size: 12px;
        color: #999;
      }

      .stock-value {
        font-size: 20px;
        font-weight: 600;
        color: #52c41a;

        &.low {
          color: #ff4d4f;
        }
      }
    }
  }

  .add-button {
    position: fixed;
    bottom: 70px;
    right: 16px;
    z-index: 100;
  }
}
</style>
