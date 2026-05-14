<template>
  <div class="device-form-container">
    <van-nav-bar
      :title="isEdit ? '编辑设备' : '新增设备'"
      left-text="取消"
      right-text="保存"
      left-arrow
      @click-left="handleCancel"
      @click-right="handleSave"
    />

    <van-form @submit="handleSubmit" class="device-form">
      <van-cell-group inset>
        <van-field
          v-model="formData.name"
          name="name"
          label="设备名称"
          placeholder="请输入设备名称"
          :rules="[{ required: true, message: '请输入设备名称' }]"
        />
        <van-field
          v-model="formData.code"
          name="code"
          label="设备编号"
          placeholder="请输入设备编号"
        />
        <van-field
          v-model="formData.typeId"
          is-link
          readonly
          name="typeId"
          label="设备类型"
          placeholder="请选择设备类型"
          @click="showTypePicker = true"
        />
        <van-field
          v-model="formData.modelId"
          is-link
          readonly
          name="modelId"
          label="设备型号"
          placeholder="请选择设备型号"
          @click="showModelPicker = true"
        />
        <van-field
          v-model="formData.topicId"
          is-link
          readonly
          name="topicId"
          label="所属专题"
          placeholder="请选择所属专题"
          @click="showTopicPicker = true"
        />
        <van-field
          v-model="formData.location"
          name="location"
          label="安装位置"
          placeholder="请输入安装位置"
        />
        <van-field
          v-model="formData.manufacturer"
          name="manufacturer"
          label="生产厂商"
          placeholder="请输入生产厂商"
        />
        <van-field
          v-model="formData.purchaseDate"
          is-link
          readonly
          name="purchaseDate"
          label="购入日期"
          placeholder="请选择购入日期"
          @click="showDatePicker = true"
        />
      </van-cell-group>

      <div class="form-actions">
        <van-button block type="primary" native-type="submit" :loading="loading">
          {{ isEdit ? '保存' : '创建' }}
        </van-button>
      </div>
    </van-form>

    <van-popup v-model:show="showTypePicker" position="bottom">
      <van-picker
        :columns="typeColumns"
        @confirm="onTypeConfirm"
        @cancel="showTypePicker = false"
      />
    </van-popup>

    <van-popup v-model:show="showModelPicker" position="bottom">
      <van-picker
        :columns="modelColumns"
        @confirm="onModelConfirm"
        @cancel="showModelPicker = false"
      />
    </van-popup>

    <van-popup v-model:show="showTopicPicker" position="bottom">
      <van-picker
        :columns="topicColumns"
        @confirm="onTopicConfirm"
        @cancel="showTopicPicker = false"
      />
    </van-popup>

    <van-popup v-model:show="showDatePicker" position="bottom">
      <van-date-picker
        v-model="currentDate"
        :min-date="minDate"
        @confirm="onDateConfirm"
        @cancel="showDatePicker = false"
      />
    </van-popup>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createDevice, updateDevice, getDeviceById } from '@/api/devices'
import { Toast } from 'vant'

const route = useRoute()
const router = useRouter()
const deviceId = computed(() => route.query.id)
const isEdit = computed(() => !!deviceId.value)

const loading = ref(false)
const showTypePicker = ref(false)
const showModelPicker = ref(false)
const showTopicPicker = ref(false)
const showDatePicker = ref(false)
const currentDate = ref(['2024', '01', '01'])
const minDate = new Date(2000, 0, 1)

const formData = ref({
  name: '',
  code: '',
  typeId: '',
  modelId: '',
  topicId: '',
  location: '',
  manufacturer: '',
  purchaseDate: ''
})

const typeColumns = [
  { text: '电梯', value: 'elevator' },
  { text: '空调', value: 'hvac' },
  { text: '消防', value: 'fire' },
  { text: '安防', value: 'security' }
]

const modelColumns = [
  { text: '型号A', value: 'model_a' },
  { text: '型号B', value: 'model_b' },
  { text: '型号C', value: 'model_c' }
]

const topicColumns = [
  { text: '默认专题', value: 'default' },
  { text: '住宅小区', value: 'residential' },
  { text: '商业楼宇', value: 'commercial' },
  { text: '工业厂房', value: 'industrial' }
]

const handleCancel = () => {
  router.back()
}

const handleSave = () => {
  document.querySelector('.device-form').dispatchEvent(new Event('submit'))
}

const handleSubmit = async () => {
  try {
    loading.value = true
    if (isEdit.value) {
      await updateDevice(deviceId.value, formData.value)
      Toast.success('修改成功')
    } else {
      await createDevice(formData.value)
      Toast.success('创建成功')
    }
    router.back()
  } catch (error) {
    console.error('Save device error:', error)
    Toast.fail('保存失败')
  } finally {
    loading.value = false
  }
}

const onTypeConfirm = ({ selectedOptions }) => {
  formData.value.typeId = selectedOptions[0].value
  showTypePicker.value = false
}

const onModelConfirm = ({ selectedOptions }) => {
  formData.value.modelId = selectedOptions[0].value
  showModelPicker.value = false
}

const onTopicConfirm = ({ selectedOptions }) => {
  formData.value.topicId = selectedOptions[0].value
  showTopicPicker.value = false
}

const onDateConfirm = ({ selectedValues }) => {
  formData.value.purchaseDate = selectedValues.join('-')
  showDatePicker.value = false
}

const loadDevice = async () => {
  if (isEdit.value) {
    try {
      const res = await getDeviceById(deviceId.value)
      const device = res.data || res
      formData.value = {
        name: device.name,
        code: device.code,
        typeId: device.typeId,
        modelId: device.modelId,
        topicId: device.topicId,
        location: device.location,
        manufacturer: device.manufacturer,
        purchaseDate: device.purchaseDate
      }
    } catch (error) {
      console.error('Load device error:', error)
    }
  }
}

onMounted(() => {
  loadDevice()
})
</script>

<style lang="scss" scoped>
.device-form-container {
  min-height: 100%;
  background: #f7f8fa;

  .device-form {
    padding: 16px 0;
  }

  .form-actions {
    padding: 16px;
  }
}
</style>
