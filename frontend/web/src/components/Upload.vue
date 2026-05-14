<template>
  <div class="upload-container">
    <van-uploader
      v-model:file-list="fileList"
      :after-read="afterRead"
      :before-read="beforeRead"
      :max-count="maxCount"
      :accept="accept"
      @delete="handleDelete"
    >
      <div class="upload-trigger" v-if="fileList.length < maxCount">
        <van-icon name="plus" size="24" />
        <span>{{ placeholder }}</span>
      </div>
    </van-uploader>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { Toast } from 'vant'

const props = defineProps({
  modelValue: {
    type: Array,
    default: () => []
  },
  maxCount: {
    type: Number,
    default: 5
  },
  accept: {
    type: String,
    default: 'image/*'
  },
  placeholder: {
    type: String,
    default: '上传图片'
  }
})

const emit = defineEmits(['update:modelValue', 'change'])

const fileList = ref(props.modelValue || [])

watch(() => props.modelValue, (val) => {
  fileList.value = val || []
})

const beforeRead = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5

  if (!isImage) {
    Toast('请上传图片文件')
    return false
  }
  if (!isLt5M) {
    Toast('图片大小不能超过5MB')
    return false
  }
  return true
}

const afterRead = (file) => {
  file.status = 'uploading'
  file.message = '上传中...'
  
  setTimeout(() => {
    file.status = 'success'
    file.message = '上传成功'
    updateValue()
  }, 1000)
}

const handleDelete = (file) => {
  updateValue()
}

const updateValue = () => {
  const urls = fileList.value.map(f => f.url || f.content)
  emit('update:modelValue', fileList.value)
  emit('change', fileList.value)
}
</script>

<style lang="scss" scoped>
.upload-container {
  :deep(.van-uploader) {
    width: 100%;
  }

  .upload-trigger {
    width: 80px;
    height: 80px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 4px;
    background: #f7f8fa;
    border-radius: 8px;
    color: #999;
    font-size: 12px;
  }
}
</style>
