import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import storage from '@/utils/storage'

export const useAppStore = defineStore('app', () => {
  const isMobile = ref(false)
  const isTablet = ref(false)
  const isDesktop = ref(false)
  const screenWidth = ref(1920)
  const screenHeight = ref(1080)
  const device = ref('desktop')
  const sidebarOpened = ref(true)
  const loading = ref(false)
  const cachedViews = ref([])
  const locale = ref(storage.get('locale') || 'zh-CN')

  const initDevice = () => {
    const width = window.innerWidth
    const height = window.innerHeight
    screenWidth.value = width
    screenHeight.value = height
    
    isMobile.value = width < 768
    isTablet.value = width >= 768 && width < 1024
    isDesktop.value = width >= 1024
    
    if (isMobile.value) {
      device.value = 'mobile'
    } else if (isTablet.value) {
      device.value = 'tablet'
    } else {
      device.value = 'desktop'
    }
  }

  const toggleSidebar = () => {
    sidebarOpened.value = !sidebarOpened.value
    storage.set('sidebarOpened', sidebarOpened.value)
  }

  const setLoading = (value) => {
    loading.value = value
  }

  const addCachedView = (view) => {
    if (view && !cachedViews.value.includes(view)) {
      cachedViews.value.push(view)
    }
  }

  const removeCachedView = (view) => {
    const index = cachedViews.value.indexOf(view)
    if (index > -1) {
      cachedViews.value.splice(index, 1)
    }
  }

  const setLocale = (newLocale) => {
    locale.value = newLocale
    storage.set('locale', newLocale)
  }

  return {
    isMobile,
    isTablet,
    isDesktop,
    screenWidth,
    screenHeight,
    device,
    sidebarOpened,
    loading,
    cachedViews,
    locale,
    initDevice,
    toggleSidebar,
    setLoading,
    addCachedView,
    removeCachedView,
    setLocale
  }
})
