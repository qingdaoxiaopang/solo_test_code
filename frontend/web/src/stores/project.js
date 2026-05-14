import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getMyProjects as apiGetMyProjects, getProjectById as apiGetProjectById } from '@/api/projects'
import storage from '@/utils/storage'

export const useProjectStore = defineStore('project', () => {
  const projects = ref([])
  const currentProject = ref(null)
  const projectLoading = ref(false)

  const currentProjectId = computed(() => currentProject.value?.id)
  const currentProjectName = computed(() => currentProject.value?.name || '')
  const projectOptions = computed(() => {
    return projects.value.map(p => ({
      label: p.name,
      value: p.id,
      ...p
    }))
  })

  const getMyProjects = async () => {
    try {
      projectLoading.value = true
      const res = await apiGetMyProjects()
      projects.value = res.data || res || []
      if (projects.value.length > 0 && !currentProject.value) {
        const savedProjectId = storage.get('currentProjectId')
        const savedProject = projects.value.find(p => p.id === savedProjectId)
        if (savedProject) {
          currentProject.value = savedProject
        } else {
          currentProject.value = projects.value[0]
        }
        storage.set('currentProjectId', currentProject.value.id)
      }
      return projects.value
    } catch (error) {
      throw error
    } finally {
      projectLoading.value = false
    }
  }

  const switchProject = async (projectId) => {
    const project = projects.value.find(p => p.id === projectId)
    if (project) {
      currentProject.value = project
      storage.set('currentProjectId', projectId)
      return project
    }
    throw new Error('Project not found')
  }

  const getProjectById = async (id) => {
    try {
      const res = await apiGetProjectById(id)
      const project = res.data || res
      return project
    } catch (error) {
      throw error
    }
  }

  const setCurrentProject = (project) => {
    currentProject.value = project
    if (project) {
      storage.set('currentProjectId', project.id)
    }
  }

  const initProject = async () => {
    if (projects.value.length === 0) {
      await getMyProjects()
    }
  }

  const clearProject = () => {
    projects.value = []
    currentProject.value = null
    storage.remove('currentProjectId')
  }

  return {
    projects,
    currentProject,
    projectLoading,
    currentProjectId,
    currentProjectName,
    projectOptions,
    getMyProjects,
    switchProject,
    getProjectById,
    setCurrentProject,
    initProject,
    clearProject
  }
})
