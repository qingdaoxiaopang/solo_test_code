<template>
  <div class="form-container">
    <slot></slot>
  </div>
</template>

<script setup>
defineProps({
  modelValue: {
    type: Object,
    default: () => ({})
  }
})
</script>

<style lang="scss" scoped>
.form-container {
  width: 100%;
}
</style>
