<template>
  <div class="login-container">
    <template v-if="!isMobile">
      <div class="login-left">
        <div class="login-banner">
          <h1>设备安装运维平台</h1>
          <p>专业的设备全生命周期管理解决方案</p>
          <div class="features">
            <div class="feature-item">
              <van-icon name="apps-o" size="32" />
              <span>设备管理</span>
            </div>
            <div class="feature-item">
              <van-icon name="setting-o" size="32" />
              <span>智能巡检</span>
            </div>
            <div class="feature-item">
              <van-icon name="warning-o" size="32" />
              <span>故障预警</span>
            </div>
            <div class="feature-item">
              <van-icon name="chart-trending-o" size="32" />
              <span>数据分析</span>
            </div>
          </div>
        </div>
      </div>
    </template>
    <div class="login-right">
      <div class="login-form-wrapper">
        <h2 class="login-title">{{ isMobile ? '设备运维平台' : '欢迎登录' }}</h2>
        <p class="login-subtitle">{{ isMobile ? '专业的设备全生命周期管理解决方案' : '请输入您的账号密码' }}</p>
        
        <van-form @submit="handleLogin" class="login-form">
          <van-cell-group inset>
            <van-field
              v-model="loginForm.username"
              name="username"
              label="账号"
              placeholder="请输入账号"
              :rules="[{ required: true, message: '请输入账号' }]"
            />
            <van-field
              v-model="loginForm.password"
              type="password"
              name="password"
              label="密码"
              placeholder="请输入密码"
              :rules="[{ required: true, message: '请输入密码' }]"
            />
          </van-cell-group>
          
          <div class="form-options">
            <van-checkbox v-model="rememberMe" shape="square">记住密码</van-checkbox>
          </div>
          
          <div class="login-button-wrapper">
            <van-button
              round
              block
              type="primary"
              native-type="submit"
              :loading="loading"
              :loading-text="loadingText"
            >
              登 录
            </van-button>
          </div>
        </van-form>
        
        <div class="login-footer" v-if="!isMobile">
          <span>Copyright © 2024 设备运维平台</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useProjectStore } from '@/stores/project'
import { useAppStore } from '@/stores/app'
import { Toast } from 'vant'

const router = useRouter()
const userStore = useUserStore()
const projectStore = useProjectStore()
const appStore = useAppStore()

const isMobile = computed(() => appStore.isMobile)
const loginForm = ref({
  username: '',
  password: ''
})
const rememberMe = ref(false)
const loading = ref(false)
const loadingText = ref('登录中...')

const handleLogin = async () => {
  try {
    loading.value = true
    await userStore.login(loginForm.value)
    await projectStore.getMyProjects()
    Toast.success('登录成功')
    router.push('/dashboard')
  } catch (error) {
    console.error('Login error:', error)
    Toast.fail(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  min-height: 100vh;
  background: #fff;

  .login-left {
    flex: 1;
    background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 40px;

    .login-banner {
      color: #fff;
      text-align: center;
      max-width: 500px;

      h1 {
        font-size: 42px;
        font-weight: 600;
        margin-bottom: 20px;
      }

      p {
        font-size: 18px;
        opacity: 0.9;
        margin-bottom: 60px;
      }

      .features {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 30px;

        .feature-item {
          display: flex;
          flex-direction: column;
          align-items: center;
          gap: 12px;
          padding: 24px;
          background: rgba(255, 255, 255, 0.1);
          border-radius: 12px;
          transition: all 0.3s;

          &:hover {
            background: rgba(255, 255, 255, 0.2);
            transform: translateY(-5px);
          }

          span {
            font-size: 16px;
            font-weight: 500;
          }
        }
      }
    }
  }

  .login-right {
    width: 480px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 40px;

    .login-form-wrapper {
      width: 100%;
      max-width: 360px;

      .login-title {
        font-size: 28px;
        font-weight: 600;
        color: #333;
        margin-bottom: 8px;
      }

      .login-subtitle {
        font-size: 14px;
        color: #999;
        margin-bottom: 40px;
      }

      .login-form {
        .form-options {
          padding: 16px 16px;
        }

        .login-button-wrapper {
          padding: 24px 16px;

          :deep(.van-button) {
            height: 44px;
            font-size: 16px;
          }
        }
      }

      .login-footer {
        text-align: center;
        margin-top: 60px;
        color: #999;
        font-size: 12px;
      }
    }
  }
}

.is-mobile {
  .login-container {
    flex-direction: column;

    .login-left {
      display: none;
    }

    .login-right {
      width: 100%;
      min-height: 100vh;

      .login-form-wrapper {
        padding: 20px;

        .login-title {
          font-size: 24px;
          text-align: center;
        }

        .login-subtitle {
          text-align: center;
        }
      }
    }
  }
}
</style>
