<template>
  <div class="table-container">
    <van-list
      v-model:loading="loading"
      :finished="finished"
      :finished-text="finishedText"
      @load="onLoad"
    >
      <slot></slot>
    </van-list>
  </div>
</template>

<script setup>
defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  finished: {
    type: Boolean,
    default: false
  },
  finishedText: {
    type: String,
    default: '没有更多了'
  }
})

const emit = defineEmits(['load'])

const onLoad = () => {
  emit('load')
}
</script>

<style lang="scss" scoped>
.table-container {
  width: 100%;
}
</style>
