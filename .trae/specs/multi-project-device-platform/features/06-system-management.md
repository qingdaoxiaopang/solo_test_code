# 系统管理模块

## 1. 模块概述

系统管理模块负责管理系统的用户权限、字典数据、系统配置和操作日志等系统级功能。

## 2. 功能清单

### 2.1 用户管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 用户列表 | 展示所有用户 | P0 |
| 用户新增 | 创建新用户 | P0 |
| 用户编辑 | 编辑用户信息 | P0 |
| 用户删除 | 删除用户 | P1 |
| 密码重置 | 重置用户密码 | P0 |
| 密码修改 | 用户修改自己密码 | P0 |
| 用户详情 | 查看用户信息 | P0 |

### 2.2 角色管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 角色列表 | 展示所有角色 | P0 |
| 角色新增 | 创建新角色 | P0 |
| 角色编辑 | 编辑角色信息 | P0 |
| 角色删除 | 删除角色 | P1 |
| 角色授权 | 分配角色权限 | P0 |

### 2.3 权限管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 权限列表 | 展示所有权限 | P0 |
| 权限新增 | 添加新权限 | P1 |
| 权限编辑 | 编辑权限信息 | P1 |
| 权限删除 | 删除权限 | P1 |
| 菜单配置 | 配置系统菜单 | P1 |

### 2.4 部门管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 部门列表 | 展示部门树形结构 | P0 |
| 部门新增 | 创建新部门 | P0 |
| 部门编辑 | 编辑部门信息 | P0 |
| 部门删除 | 删除部门 | P1 |
| 用户归属 | 用户归属部门管理 | P0 |

### 2.5 字典管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 字典类型 | 字典类型管理 | P0 |
| 字典项 | 字典项数据管理 | P0 |
| 数据缓存 | 字典数据缓存 | P1 |
| 导入导出 | 字典数据导入导出 | P2 |

### 2.6 操作日志

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 日志列表 | 展示操作日志 | P0 |
| 日志查询 | 多条件查询日志 | P0 |
| 日志导出 | 导出日志数据 | P1 |
| 日志清理 | 定期清理日志 | P1 |

### 2.7 系统设置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 基本设置 | 系统基本配置 | P1 |
| 通知设置 | 站内消息配置 | P2 |
| 安全设置 | 安全策略配置 | P1 |

## 3. 预定义角色

| 角色编码 | 角色名称 | 说明 |
|----------|----------|------|
| SUPER_ADMIN | 超级管理员 | 系统最高权限 |
| PROJECT_ADMIN | 项目管理员 | 项目管理权限 |
| OPERATION_ENGINEER | 运维工程师 | 运维操作权限 |
| FIELD_STAFF | 现场人员 | 现场执行权限 |

## 4. 数据模型

### 4.1 用户表 (sys_user)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名（唯一） |
| password | VARCHAR(100) | 密码（加密） |
| real_name | VARCHAR(50) | 真实姓名 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| avatar | VARCHAR(500) | 头像URL |
| dept_id | BIGINT | 部门ID |
| status | VARCHAR(20) | 状态 |
| last_login_time | DATETIME | 最后登录时间 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 4.2 角色表 (sys_role)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| code | VARCHAR(50) | 角色编码 |
| name | VARCHAR(50) | 角色名称 |
| description | VARCHAR(200) | 描述 |
| status | VARCHAR(20) | 状态 |
| create_time | DATETIME | 创建时间 |

### 4.3 权限表 (sys_permission)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| parent_id | BIGINT | 父权限ID |
| name | VARCHAR(50) | 权限名称 |
| code | VARCHAR(100) | 权限编码 |
| type | VARCHAR(20) | 权限类型 |
| url | VARCHAR(200) | 权限URL |
| method | VARCHAR(20) | 请求方法 |
| sort | INT | 排序 |
| create_time | DATETIME | 创建时间 |

### 4.4 字典表 (sys_dict)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(50) | 字典名称 |
| code | VARCHAR(50) | 字典编码 |
| description | VARCHAR(200) | 描述 |
| create_time | DATETIME | 创建时间 |

### 4.5 字典项表 (sys_dict_item)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| dict_id | BIGINT | 字典ID |
| label | VARCHAR(50) | 标签 |
| value | VARCHAR(100) | 值 |
| sort | INT | 排序 |
| status | VARCHAR(20) | 状态 |
| create_time | DATETIME | 创建时间 |

### 4.6 操作日志表 (sys_log)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
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
| create_time | DATETIME | 操作时间 |

## 5. API接口

### 5.1 用户接口

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

### 5.2 角色接口

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

### 5.3 权限接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/permissions | 权限列表（树形） |
| POST | /api/permissions | 创建权限 |
| PUT | /api/permissions/{id} | 更新权限 |
| DELETE | /api/permissions/{id} | 删除权限 |

### 5.4 部门接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/departments | 部门列表（树形） |
| POST | /api/departments | 创建部门 |
| PUT | /api/departments/{id} | 更新部门 |
| DELETE | /api/departments/{id} | 删除部门 |

### 5.5 字典接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/dicts | 字典类型列表 |
| POST | /api/dicts | 创建字典 |
| PUT | /api/dicts/{id} | 更新字典 |
| DELETE | /api/dicts/{id} | 删除字典 |
| GET | /api/dicts/{code}/items | 字典项列表 |
| POST | /api/dicts/{dictId}/items | 创建字典项 |
| PUT | /api/dicts/items/{id} | 更新字典项 |
| DELETE | /api/dicts/items/{id} | 删除字典项 |

### 5.6 日志接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/logs | 日志列表 |
| GET | /api/logs/{id} | 日志详情 |
| DELETE | /api/logs | 清理日志 |

## 6. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 用户管理 | /system/users | 用户列表 |
| 角色管理 | /system/roles | 角色列表 |
| 权限管理 | /system/permissions | 权限配置 |
| 部门管理 | /system/departments | 部门列表 |
| 字典管理 | /system/dicts | 字典管理 |
| 操作日志 | /system/logs | 日志列表 |
| 系统设置 | /system/settings | 系统设置 |

## 7. 权限控制

| 角色 | 用户管理 | 角色管理 | 权限管理 | 日志查看 |
|------|----------|----------|----------|----------|
| 超级管理员 | 完全控制 | 完全控制 | 完全控制 | 是 |
| 项目管理员 | 查看+编辑 | 查看 | 否 | 是 |
| 运维工程师 | 否 | 否 | 否 | 否 |
| 现场人员 | 否 | 否 | 否 | 否 |
