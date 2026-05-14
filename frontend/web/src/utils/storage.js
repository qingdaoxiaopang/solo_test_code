const TOKEN_KEY = 'token'
const USER_KEY = 'userInfo'
const PROJECT_KEY = 'currentProjectId'
const SIDEBAR_KEY = 'sidebarOpened'
const LOCALE_KEY = 'locale'

const storage = {
  get(key, defaultValue = null) {
    const item = localStorage.getItem(key)
    if (!item) return defaultValue
    try {
      return JSON.parse(item)
    } catch {
      return item
    }
  },

  set(key, value) {
    const item = typeof value === 'string' ? value : JSON.stringify(value)
    localStorage.setItem(key, item)
  },

  remove(key) {
    localStorage.removeItem(key)
  },

  clear() {
    localStorage.clear()
  },

  getToken() {
    return this.get(TOKEN_KEY)
  },

  setToken(token) {
    this.set(TOKEN_KEY, token)
  },

  removeToken() {
    this.remove(TOKEN_KEY)
  },

  getUserInfo() {
    return this.get(USER_KEY)
  },

  setUserInfo(userInfo) {
    this.set(USER_KEY, userInfo)
  },

  removeUserInfo() {
    this.remove(USER_KEY)
  },

  getCurrentProjectId() {
    return this.get(PROJECT_KEY)
  },

  setCurrentProjectId(id) {
    this.set(PROJECT_KEY, id)
  },

  removeCurrentProjectId() {
    this.remove(PROJECT_KEY)
  }
}

export default storage
export { TOKEN_KEY, USER_KEY, PROJECT_KEY, SIDEBAR_KEY, LOCALE_KEY }
