# 系统管理模块

## 1. 模块概述

系统管理模块负责管理系统的用户权限、字典数据、系统配置、操作日志以及平台首页仪表盘等系统级功能，是整个平台的基础支撑模块。系统使用Redis作为缓存中间件，MinIO作为对象存储服务。

## 2. 用户故事

作为系统管理员，我需要登录平台管理系统。登录时输入用户名密码和验证码，支持7天记住登录状态。进入系统后，首页展示平台统计概览：项目数量、设备数量、待处理任务、告警统计等。我可以管理用户，创建新用户、编辑用户信息、重置密码、禁用/启用用户账号，还能上传用户头像。角色管理方面，我可以创建角色并为角色分配菜单和按钮权限。系统配置包括平台名称、Logo设置，以及邮件、短信等通知配置。所有操作都会记录到操作日志中，支持日志查询和导出。

## 3. 功能清单

### 2.1 认证模块（登录页）

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 用户名密码登录 | 用户名+密码认证登录 | P0 |
| 微信小程序登录 | 微信授权登录，绑定用户账号 | P0 |
| 记住登录状态 | 7天免登录 | P1 |
| 验证码 | 图形验证码防暴力破解 | P0 |
| 登录错误提示 | 错误次数限制提示 | P0 |
| 退出登录 | 清除会话信息 | P0 |

### 2.2 概览模块（首页仪表盘）

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 平台统计概览 | 核心数据统计卡片展示 | P0 |
| 项目数量统计 | 平台项目总数、在线项目数 | P0 |
| 设备数量统计 | 设备总数、在线设备数、告警设备数 | P0 |
| 待处理任务统计 | 待处理工单数、待巡检任务数 | P0 |
| 告警统计 | 今日告警、告警趋势图表 | P0 |
| 快捷入口 | 常用功能快捷访问 | P1 |
| 消息通知 | 系统消息、待办事项提醒 | P1 |

### 2.3 用户管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 用户列表 | 展示所有用户，支持分页、搜索、筛选 | P0 |
| 用户新增 | 创建新用户 | P0 |
| 用户编辑 | 编辑用户信息 | P0 |
| 用户禁用/启用 | 启用或禁用用户账号 | P0 |
| 重置密码 | 重置用户密码为默认密码 | P0 |
| 密码修改 | 用户修改自己密码 | P0 |
| 用户详情 | 查看用户信息 | P0 |
| 用户头像上传 | 上传用户头像到MinIO | P1 |

### 2.4 角色管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 角色列表 | 展示所有角色 | P0 |
| 角色新增 | 创建新角色 | P0 |
| 角色编辑 | 编辑角色信息 | P0 |
| 角色删除 | 删除角色 | P1 |
| 权限分配 | 分配角色权限 | P0 |

### 2.5 系统配置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 基础配置 | 平台名称、Logo配置 | P0 |
| 邮件配置 | 邮件服务器、SMTP配置 | P1 |
| 短信配置 | 短信服务商配置 | P2 |
| 通知配置 | 站内通知配置 | P2 |

### 2.6 操作日志

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 日志列表 | 展示操作日志，支持分页、条件查询 | P0 |
| 日志详情 | 查看单条日志详细信息 | P0 |
| 日志导出 | 导出日志数据（Excel格式） | P1 |
| 日志清理 | 定期清理过期日志 | P1 |

## 3. Redis缓存使用场景

| 场景 | 说明 |
|------|------|
| 会话管理 | 存储用户登录Token和会话信息 |
| 字典数据缓存 | 缓存系统字典数据，避免频繁查询数据库 |
| 首页仪表盘数据缓存 | 缓存统计数据，提升页面响应速度 |
| 接口限流 | 基于Redis实现接口访问频率限制 |
| 用户信息缓存 | 缓存当前登录用户信息，减少数据库查询 |

## 4. MinIO对象存储使用场景

| 场景 | 说明 |
|------|------|
| 用户头像存储 | 存储用户头像图片 |
| 平台Logo存储 | 存储平台Logo图片 |
| 日志导出文件存储 | 临时存储日志导出的Excel文件 |

## 5. 预定义角色

| 角色编码 | 角色名称 | 说明 |
|----------|----------|------|
| SUPER_ADMIN | 超级管理员 | 系统最高权限 |
| PROJECT_ADMIN | 项目管理员 | 项目管理权限 |
| OPERATION_ENGINEER | 运维工程师 | 运维操作权限 |
| FIELD_STAFF | 现场人员 | 现场执行权限 |

## 6. 数据模型

### 6.1 用户表 (sys_user)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除（0-未删除，1-已删除） |
| remark | VARCHAR(500) | 备注 |
| username | VARCHAR(50) | 用户名（唯一） |
| password | VARCHAR(100) | 密码（加密） |
| real_name | VARCHAR(50) | 真实姓名 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| avatar | VARCHAR(500) | 头像URL（存储在MinIO） |
| dept_id | BIGINT | 部门ID |
| status | VARCHAR(20) | 状态（ACTIVE-正常，DISABLED-禁用） |
| last_login_time | TIMESTAMP | 最后登录时间 |
| wx_openid | VARCHAR(100) | 微信小程序openid |
| wx_unionid | VARCHAR(100) | 微信unionid |
| wx_nickname | VARCHAR(100) | 微信昵称 |

### 6.2 角色表 (sys_role)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| code | VARCHAR(50) | 角色编码 |
| name | VARCHAR(50) | 角色名称 |
| description | VARCHAR(200) | 描述 |
| status | VARCHAR(20) | 状态 |

### 6.3 权限表 (sys_permission)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| parent_id | BIGINT | 父权限ID |
| name | VARCHAR(50) | 权限名称 |
| code | VARCHAR(100) | 权限编码 |
| type | VARCHAR(20) | 权限类型（MENU-菜单，BUTTON-按钮，API-接口） |
| url | VARCHAR(200) | 权限URL |
| method | VARCHAR(20) | 请求方法 |
| sort | INT | 排序 |

### 6.4 字典表 (sys_dict)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| name | VARCHAR(50) | 字典名称 |
| code | VARCHAR(50) | 字典编码 |
| description | VARCHAR(200) | 描述 |

### 6.5 字典项表 (sys_dict_item)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| dict_id | BIGINT | 字典ID |
| label | VARCHAR(50) | 标签 |
| value | VARCHAR(100) | 值 |
| sort | INT | 排序 |
| status | VARCHAR(20) | 状态 |

### 6.6 操作日志表 (sys_log)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| user_id | BIGINT | 用户ID |
| username | VARCHAR(50) | 用户名 |
| operation | VARCHAR(50) | 操作类型 |
| module | VARCHAR(50) | 模块 |
| method | VARCHAR(100) | 方法 |
| url | VARCHAR(200) | 请求URL |
| ip | VARCHAR(50) | IP地址 |
| params | TEXT | 请求参数 |
| result | TEXT | 返回结果 |
| error_msg | TEXT | 错误信息 |
| duration | INT | 耗时(ms) |

### 6.7 部门表 (sys_dept)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| parent_id | BIGINT | 父部门ID |
| name | VARCHAR(50) | 部门名称 |
| code | VARCHAR(50) | 部门编码 |
| leader | VARCHAR(50) | 负责人 |
| phone | VARCHAR(20) | 联系电话 |
| sort | INT | 排序 |

### 6.8 用户角色关联表 (sys_user_role)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除 |
| remark | VARCHAR(500) | 备注 |
| user_id | BIGINT | 用户ID |
| role_id | BIGINT | 角色ID |

## 7. API接口

### 7.1 认证接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/auth/login | 用户登录 |
| POST | /api/auth/wx-miniapp-login | 微信小程序登录 |
| POST | /api/auth/logout | 退出登录 |
| GET | /api/auth/userinfo | 获取当前用户信息 |
| PUT | /api/auth/password | 修改密码 |

### 7.2 文件上传接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/file/upload | 上传文件到MinIO |
| DELETE | /api/file/delete | 删除MinIO中的文件 |
| GET | /api/file/download | 从MinIO下载文件 |

### 7.3 首页统计接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/dashboard/stats | 获取首页统计数据 |
| GET | /api/dashboard/project-stats | 项目统计 |
| GET | /api/dashboard/device-stats | 设备统计 |
| GET | /api/dashboard/task-stats | 待处理任务统计 |
| GET | /api/dashboard/alert-stats | 告警统计 |

### 7.4 用户接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/users | 用户列表 |
| POST | /api/users | 创建用户 |
| GET | /api/users/{id} | 用户详情 |
| PUT | /api/users/{id} | 更新用户 |
| DELETE | /api/users/{id} | 删除用户 |
| PUT | /api/users/{id}/password | 重置密码 |
| PUT | /api/users/password | 修改密码 |
| PUT | /api/users/{id}/status | 修改状态 |
| POST | /api/users/{id}/avatar | 上传用户头像 |

### 7.5 角色接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/roles | 角色列表 |
| POST | /api/roles | 创建角色 |
| GET | /api/roles/{id} | 角色详情 |
| PUT | /api/roles/{id} | 更新角色 |
| DELETE | /api/roles/{id} | 删除角色 |
| GET | /api/roles/{id}/permissions | 角色权限 |
| PUT | /api/roles/{id}/permissions | 分配权限 |
| POST | /api/users/{userId}/roles | 分配用户角色 |

### 7.6 权限接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/permissions | 权限列表（树形） |
| POST | /api/permissions | 创建权限 |
| PUT | /api/permissions/{id} | 更新权限 |
| DELETE | /api/permissions/{id} | 删除权限 |

### 7.7 部门接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/departments | 部门列表（树形） |
| POST | /api/departments | 创建部门 |
| PUT | /api/departments/{id} | 更新部门 |
| DELETE | /api/departments/{id} | 删除部门 |

### 7.8 系统配置接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/system/config | 获取系统配置 |
| PUT | /api/system/config | 更新系统配置 |
| GET | /api/system/config/{key} | 获取指定配置项 |
| PUT | /api/system/config/{key} | 更新指定配置项 |

### 7.9 日志接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/logs | 日志列表 |
| GET | /api/logs/{id} | 日志详情 |
| DELETE | /api/logs | 清理日志 |
| GET | /api/logs/export | 导出日志 |

## 8. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 登录页 | /login | 用户登录 |
| 首页仪表盘 | /dashboard | 平台统计概览 |
| 用户管理 | /system/users | 用户列表 |
| 角色管理 | /system/roles | 角色列表 |
| 权限管理 | /system/permissions | 权限配置 |
| 部门管理 | /system/departments | 部门列表 |
| 字典管理 | /system/dicts | 字典管理 |
| 操作日志 | /system/logs | 日志列表 |
| 系统配置 | /system/settings | 系统设置 |

## 9. 验证标准

### 9.1 单元测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 用户CRUD | 用户增删改查 | 功能正常 | P0 |
| 权限验证 | 权限控制逻辑 | 权限验证正确 | P0 |
| 密码加密 | 密码加密存储 | 加密正确 | P0 |
| 字典缓存 | 字典数据缓存 | 缓存正确更新 | P1 |
| 登录认证 | Token生成和验证 | 认证正确 | P0 |
| 仪表盘统计 | 统计数据准确性 | 数据准确 | P0 |
| Redis缓存操作 | 缓存读写正常 | 缓存功能正常 | P0 |
| MinIO文件上传 | 文件上传下载 | 功能正常 | P0 |

### 9.2 集成测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 登录验证 | 用户登录登出 | 验证正确 | P0 |
| 权限拦截 | 无权限访问拦截 | 正确拦截 | P0 |
| 审计日志 | 操作日志记录 | 日志正确记录 | P0 |
| 会话管理 | Token过期验证 | 正确处理过期 | P0 |
| Redis会话存储 | Token存储在Redis中 | 正常存取正确 | P0 |
| MinIO文件存储 | 文件存储在MinIO中 | 文件可正常上传下载 | P0 |

### 9.3 UI交互验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 登录页 | 登录表单验证 | 验证正确提示 | P0 |
| 仪表盘 | 统计卡片加载 | 加载正常，数据准确 | P0 |
| 权限树 | 权限树选择 | 树形结构正确 | P0 |
| 角色分配 | 角色权限分配 | 操作正确保存 | P0 |
| 密码修改 | 密码修改流程 | 修改验证正确 | P0 |
| 文件上传 | 用户头像上传 | 上传成功显示预览 | P1 |

### 9.4 验收测试用例

#### 测试用例 01: 用户登录流程

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 输入正确的用户名和密码 | 登录成功，跳转首页，Token存储在Redis |
| 2 | 查看首页仪表盘 | 显示项目统计、设备统计、待处理任务等 |
| 3 | 点击退出登录 | 退出成功，跳转登录页，Redis中的Token清除 |
| 4 | 输入错误的密码 | 提示密码错误，验证码刷新 |
| 5 | 连续5次登录失败 | 账号被锁定提示 |

#### 测试用例 02: 完整权限流程

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 超级管理员创建用户 | 用户创建成功，字典数据缓存到Redis |
| 2 | 分配角色给用户 | 角色分配正确 |
| 3 | 配置角色权限 | 权限配置正确 |
| 4 | 用户登录验证 | 权限正常登录，用户信息缓存到Redis |
| 5 | 无权限功能检查 | 无权限被正确拦截 |
| 6 | 用户操作记录 | 操作日志正确记录 |

#### 测试用例 03: 仪表盘数据展示

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 登录后进入首页 | 自动加载仪表盘数据，数据从Redis缓存读取 |
| 2 | 查看项目统计卡片 | 显示项目总数、在线项目数 |
| 3 | 查看设备统计卡片 | 显示设备总数、在线设备、告警设备 |
| 4 | 查看待处理任务 | 显示待处理工单数、待巡检任务数 |
| 5 | 查看告警统计 | 显示今日告警数量、告警趋势图 |

#### 测试用例 04: 用户头像上传

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 进入用户详情页 | 显示用户头像上传按钮 |
| 2 | 点击上传头像 | 选择图片上传到MinIO |
| 3 | 上传成功后 | 用户头像显示 |
| 4 | 刷新页面 | 用户头像正常显示 |
