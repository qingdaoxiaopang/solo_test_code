<template>
  <div class="layout-container">
    <slot></slot>
  </div>
</template>

<script setup>
defineProps({
  showSidebar: {
    type: Boolean,
    default: true
  },
  showHeader: {
    type: Boolean,
    default: true
  }
})
</script>

<style lang="scss" scoped>
.layout-container {
  width: 100%;
  height: 100%;
}
</style>
