import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useProjectStore } from '@/stores/project'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/auth/Login.vue'),
    meta: { title: '登录', showNavBar: false, showTabBar: false }
  },
  {
    path: '/',
    redirect: '/dashboard',
    meta: { showNavBar: false, showTabBar: false }
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('@/views/dashboard/Index.vue'),
    meta: { title: '首页', icon: 'home' }
  },
  {
    path: '/project',
    name: 'project',
    redirect: '/project/list',
    meta: { title: '项目管理', icon: 'cluster' },
    children: [
      {
        path: 'list',
        name: 'projectList',
        component: () => import('@/views/project/List.vue'),
        meta: { title: '项目列表' }
      },
      {
        path: 'detail/:id',
        name: 'projectDetail',
        component: () => import('@/views/project/Detail.vue'),
        meta: { title: '项目详情' }
      },
      {
        path: 'form',
        name: 'projectForm',
        component: () => import('@/views/project/Form.vue'),
        meta: { title: '项目表单' }
      }
    ]
  },
  {
    path: '/device',
    name: 'device',
    redirect: '/device/list',
    meta: { title: '设备管理', icon: 'app' },
    children: [
      {
        path: 'list',
        name: 'deviceList',
        component: () => import('@/views/device/List.vue'),
        meta: { title: '设备列表' }
      },
      {
        path: 'detail/:id',
        name: 'deviceDetail',
        component: () => import('@/views/device/Detail.vue'),
        meta: { title: '设备详情' }
      },
      {
        path: 'form',
        name: 'deviceForm',
        component: () => import('@/views/device/Form.vue'),
        meta: { title: '设备表单' }
      },
      {
        path: 'inspection-plans',
        name: 'inspectionPlans',
        component: () => import('@/views/device/InspectionPlans.vue'),
        meta: { title: '巡检计划' }
      },
      {
        path: 'maintenance-orders',
        name: 'maintenanceOrders',
        component: () => import('@/views/device/MaintenanceOrders.vue'),
        meta: { title: '维护工单' }
      },
      {
        path: 'faults',
        name: 'deviceFaults',
        component: () => import('@/views/device/Faults.vue'),
        meta: { title: '故障管理' }
      }
    ]
  },
  {
    path: '/config',
    name: 'config',
    redirect: '/config/device-types',
    meta: { title: '业务配置', icon: 'setting' },
    children: [
      {
        path: 'device-types',
        name: 'deviceTypes',
        component: () => import('@/views/config/DeviceTypes.vue'),
        meta: { title: '设备类型' }
      },
      {
        path: 'manufacturers',
        name: 'manufacturers',
        component: () => import('@/views/config/Manufacturers.vue'),
        meta: { title: '设备厂商' }
      },
      {
        path: 'device-models',
        name: 'deviceModels',
        component: () => import('@/views/config/DeviceModels.vue'),
        meta: { title: '设备型号' }
      },
      {
        path: 'topics',
        name: 'topics',
        component: () => import('@/views/config/Topics.vue'),
        meta: { title: '专题管理' }
      },
      {
        path: 'spare-parts',
        name: 'spareParts',
        component: () => import('@/views/config/SpareParts.vue'),
        meta: { title: '备件库' }
      },
      {
        path: 'maintenance-companies',
        name: 'maintenanceCompanies',
        component: () => import('@/views/config/MaintenanceCompanies.vue'),
        meta: { title: '维护公司' }
      }
    ]
  },
  {
    path: '/system',
    name: 'system',
    redirect: '/system/users',
    meta: { title: '系统管理', icon: 'system' },
    children: [
      {
        path: 'users',
        name: 'systemUsers',
        component: () => import('@/views/system/Users.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'roles',
        name: 'roles',
        component: () => import('@/views/system/Roles.vue'),
        meta: { title: '角色管理' }
      },
      {
        path: 'permissions',
        name: 'permissions',
        component: () => import('@/views/system/Permissions.vue'),
        meta: { title: '权限管理' }
      },
      {
        path: 'dicts',
        name: 'dicts',
        component: () => import('@/views/system/Dicts.vue'),
        meta: { title: '字典管理' }
      },
      {
        path: 'logs',
        name: 'logs',
        component: () => import('@/views/system/Logs.vue'),
        meta: { title: '日志管理' }
      },
      {
        path: 'settings',
        name: 'systemSettings',
        component: () => import('@/views/system/Settings.vue'),
        meta: { title: '系统配置' }
      },
      {
        path: 'mine',
        name: 'mine',
        component: () => import('@/views/system/Settings.vue'),
        meta: { title: '个人中心' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const projectStore = useProjectStore()
  
  const title = to.meta?.title
  if (title) {
    document.title = `${title} - 设备运维平台`
  }

  if (to.path === '/login') {
    next()
    return
  }

  if (!userStore.token) {
    next('/login')
    return
  }

  if (!userStore.userInfo) {
    try {
      await userStore.getUserInfo()
      await projectStore.initProject()
    } catch (error) {
      next('/login')
      return
    }
  }

  next()
})

export default router
