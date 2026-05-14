import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from './router'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'vant/lib/index.css'
import * as VueRouter from 'vue-router'
import {
  Button,
  Cell,
  CellGroup,
  Field,
  Form,
  NavBar,
  Tabbar,
  TabbarItem,
  Icon,
  List,
  PullRefresh,
  ActionSheet,
  Popup,
  Dialog,
  Toast,
  Loading,
  Empty,
  Badge,
  Tag,
  Card,
  Image as VanImage,
  Divider,
  Search,
  DropdownMenu,
  DropdownItem,
  Picker,
  DatePicker,
  Cascader,
  Uploader,
  Progress,
  Steps,
  Step,
  Tabs,
  Tab,
  TreeSelect,
  Sidebar,
  SidebarItem,
  Skeleton,
  SwipeCell,
  Area
} from 'vant'
import './styles/common.scss'
import './styles/element-plus.scss'
import './styles/vant.scss'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus)

app.use(Button)
app.use(Cell)
app.use(CellGroup)
app.use(Field)
app.use(Form)
app.use(NavBar)
app.use(Tabbar)
app.use(TabbarItem)
app.use(Icon)
app.use(List)
app.use(PullRefresh)
app.use(ActionSheet)
app.use(Popup)
app.use(Dialog)
app.use(Toast)
app.use(Loading)
app.use(Empty)
app.use(Badge)
app.use(Tag)
app.use(Card)
app.use(VanImage)
app.use(Divider)
app.use(Search)
app.use(DropdownMenu)
app.use(DropdownItem)
app.use(Picker)
app.use(DatePicker)
app.use(Cascader)
app.use(Uploader)
app.use(Progress)
app.use(Steps)
app.use(Step)
app.use(Tabs)
app.use(Tab)
app.use(TreeSelect)
app.use(Sidebar)
app.use(SidebarItem)
app.use(Skeleton)
app.use(SwipeCell)
app.use(Area)

app.mount('#app')
