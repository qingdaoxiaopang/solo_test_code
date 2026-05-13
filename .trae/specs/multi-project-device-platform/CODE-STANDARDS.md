# Java代码编码规范

## 1. 命名规范

### 1.1 项目/包命名

- 包名全部小写，使用点号分隔，域名倒置：`com.operation`
- 业务包名使用模块前缀：`com.operation.module.project`、`com.operation.module.device`

### 1.2 类/接口命名

- 类名使用大驼峰命名法：`ProjectService`、`DeviceController`
- 接口使用`I`作为前缀（可选），或者和实现类保持一致：`IProjectService` 或 `ProjectService`
- 实体类直接使用业务名词：`Project`、`Device`
- Service实现类使用`Impl`后缀：`ProjectServiceImpl`
- Controller使用`Controller`后缀：`ProjectController`
- Mapper使用`Mapper`后缀：`ProjectMapper`
- 枚举类使用`Enum`后缀：`StatusEnum`
- 异常类使用`Exception`后缀：`BusinessException`
- DTO使用`DTO`后缀：`ProjectDTO`
- VO使用`VO`后缀：`ProjectVO`
- Query使用`Query`后缀：`ProjectQuery`

### 1.3 方法命名

- 方法名使用小驼峰命名法：`getProjectById`
- 获取单个对象用`get`前缀：`getById`
- 获取列表用`list`前缀：`listByQuery`
- 获取分页用`page`前缀：`pageByQuery`
- 新增用`save`、`create`或`add`前缀：`saveProject`
- 修改用`update`前缀：`updateProject`
- 删除用`delete`或`remove`前缀：`deleteById`
- 判断用`has`、`is`、`can`等前缀：`hasPermission`、`isAvailable`

### 1.4 变量命名

- 普通变量使用小驼峰：`userId`、`projectName`
- 常量使用全大写下划线分隔：`MAX_SIZE`、`DEFAULT_STATUS`
- 布尔类型变量避免使用`is`前缀（避免和getter方法冲突）：`enabled`而非`isEnabled`

## 2. 代码风格规范

### 2.1 缩进

- 统一使用4个空格缩进
- Tab字符设置为4个空格

### 2.2 括号

- 左大括号不换行：

```java
// 正确
public void method() {
    // ...
}

// 错误
public void method()
{
    // ...
}
```

### 2.3 空行

- 方法之间保留一个空行
- 逻辑块之间保留一个空行
- import语句分组，中间空一行

### 2.4 每行字符数

- 推荐每行不超过120个字符
- 过长表达式可换行，参数对齐

## 3. 注释规范

### 3.1 类注释

- 所有类、接口必须添加JavaDoc注释
- 说明类的职责、作者、日期等

```java
/**
 * 项目服务接口
 *
 * @author Operation Team
 * @since 2024-01-01
 */
public interface IProjectService {
}
```

### 3.2 方法注释

- public方法必须添加JavaDoc注释
- 说明方法功能、参数、返回值、异常

```java
/**
 * 根据ID获取项目信息
 *
 * @param id 项目ID
 * @return 项目信息
 * @throws BusinessException 项目不存在时抛出
 */
Project getById(Long id) throws BusinessException;
```

### 3.3 复杂逻辑注释

- 复杂业务逻辑需要添加注释说明
- 特殊业务规则需要注释

## 4. 异常处理规范

### 4.1 异常分类

- 自定义业务异常：`BusinessException`
- 参数校验异常：`ValidationException`
- 系统异常：`SystemException`

### 4.2 异常使用

- 业务错误使用业务异常，抛出错误码和信息
- 不使用`try-catch`直接吞掉异常，需要记录日志
- 不使用`Exception`捕获所有异常，需具体处理

## 5. 数据验证规范

- 使用`@Valid`、`@Validated`注解进行参数验证
- 验证注解放置在实体字段上
- 验证分组可根据场景使用

## 6. 接口返回规范

- 统一使用`Result<T>`封装返回结果
- 错误码使用统一枚举定义
- 分页返回使用`PageResult<T>`

## 7. 工具类规范

- 工具类使用`Util`或`Utils`后缀：`DateUtil`
- 工具类使用`private`构造函数，防止实例化
- 工具方法使用`static`

## 8. 日志规范

- 使用SLF4J门面
- 日志级别：ERROR > WARN > INFO > DEBUG
- 异常必须使用`logger.error("message", ex)`记录异常堆栈
- 敏感信息不记录日志：密码、密钥等

## 9. 单元测试规范

- 测试类使用`Test`后缀：`ProjectServiceTest`
- 使用JUnit5 + Mockito框架
- 测试方法使用`test`前缀：`testGetProjectById`
- Given-When-Then模式

---

# 前端代码编码规范

## 1. 命名规范

### 1.1 文件命名

- Vue组件使用PascalCase：`ProjectList.vue`、`ProjectDetail.vue`
- 工具文件使用camelCase：`dateUtil.js`
- 常量文件使用全大写下划线：`CONSTANTS.js`
- 页面组件放在`views`目录，业务组件放在`components`目录

### 1.2 Vue组件命名

- 组件名使用PascalCase
- 组件标签在模板中使用PascalCase

### 1.3 变量/函数命名

- 变量/函数使用camelCase
- 常量使用全大写下划线
- 组件内部事件使用`handle`前缀：`handleClick`、`handleSubmit`

## 2. Vue组件规范

### 2.1 组件结构顺序

```vue
<template>
  <!-- 模板 -->
</template>

<script setup>
// 导入
import { ref } from 'vue'

// props/emit
const props = defineProps()
const emit = defineEmits()

// 响应式数据
const data = ref()

// 计算属性
const computedData = computed(() => {})

// 方法
const handleClick = () => {}

// 生命周期
onMounted(() => {})
</script>

<style scoped>
/* 样式 */
</style>
```

### 2.2 Props定义

- 使用对象形式定义
- 必须声明类型和默认值（可选）
- 使用`defineProps`

## 3. 样式规范

- 使用scoped样式避免污染
- 优先使用Tailwind CSS原子类
- 复杂样式可使用class复用
- 组件内部样式使用`-`分隔的kebab-case命名

## 4. 注释规范

- 复杂逻辑添加注释
- 公共组件添加使用说明
- 接口添加简要说明
