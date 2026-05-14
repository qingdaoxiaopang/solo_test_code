<template>
  <div class="project-form-container">
    <van-nav-bar
      :title="isEdit ? '编辑项目' : '新建项目'"
      left-text="取消"
      right-text="保存"
      left-arrow
      @click-left="handleCancel"
      @click-right="handleSave"
    />

    <van-form @submit="handleSubmit" class="project-form">
      <van-cell-group inset>
        <van-field
          v-model="formData.name"
          name="name"
          label="项目名称"
          placeholder="请输入项目名称"
          :rules="[{ required: true, message: '请输入项目名称' }]"
        />
        <van-field
          v-model="formData.code"
          name="code"
          label="项目编号"
          placeholder="请输入项目编号"
        />
        <van-field
          v-model="formData.location"
          name="location"
          label="项目地址"
          placeholder="请输入项目地址"
        />
        <van-field
          v-model="formData.managerName"
          name="managerName"
          label="负责人"
          placeholder="请输入负责人姓名"
        />
        <van-field
          v-model="formData.managerPhone"
          name="managerPhone"
          label="联系电话"
          placeholder="请输入联系电话"
        />
        <van-field
          v-model="formData.description"
          name="description"
          label="项目描述"
          type="textarea"
          placeholder="请输入项目描述"
          rows="3"
        />
      </van-cell-group>

      <div class="form-actions">
        <van-button block type="primary" native-type="submit" :loading="loading">
          {{ isEdit ? '保存' : '创建' }}
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createProject, updateProject, getProjectById } from '@/api/projects'
import { Toast } from 'vant'

const route = useRoute()
const router = useRouter()
const projectId = computed(() => route.query.id)
const isEdit = computed(() => !!projectId.value)

const loading = ref(false)
const formData = ref({
  name: '',
  code: '',
  location: '',
  managerName: '',
  managerPhone: '',
  description: ''
})

const handleCancel = () => {
  router.back()
}

const handleSave = () => {
  document.querySelector('.project-form').dispatchEvent(new Event('submit'))
}

const handleSubmit = async () => {
  try {
    loading.value = true
    if (isEdit.value) {
      await updateProject(projectId.value, formData.value)
      Toast.success('修改成功')
    } else {
      await createProject(formData.value)
      Toast.success('创建成功')
    }
    router.back()
  } catch (error) {
    console.error('Save project error:', error)
    Toast.fail('保存失败')
  } finally {
    loading.value = false
  }
}

const loadProject = async () => {
  if (isEdit.value) {
    try {
      const res = await getProjectById(projectId.value)
      const project = res.data || res
      formData.value = {
        name: project.name,
        code: project.code,
        location: project.location,
        managerName: project.managerName,
        managerPhone: project.managerPhone,
        description: project.description
      }
    } catch (error) {
      console.error('Load project error:', error)
    }
  }
}

onMounted(() => {
  loadProject()
})
</script>

<style lang="scss" scoped>
.project-form-container {
  min-height: 100%;
  background: #f7f8fa;

  .project-form {
    padding: 16px 0;
  }

  .form-actions {
    padding: 16px;
  }
}
</style>
