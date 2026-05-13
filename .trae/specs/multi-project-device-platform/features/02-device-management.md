# 设备管理模块

## 1. 模块概述

设备管理模块负责管理企业所有设备资产，实现设备台账管理、全生命周期追踪、设备分类和参数管理。

## 2. 功能清单

### 2.1 设备类型管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 类型列表 | 展示设备类型树形结构 | P0 |
| 类型新增 | 创建新设备类型 | P0 |
| 类型编辑 | 修改设备类型信息 | P0 |
| 类型删除 | 删除设备类型（需确认） | P1 |

### 2.2 设备台账管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备列表 | 展示所有设备，支持分页、搜索、筛选 | P0 |
| 设备新增 | 创建设备台账 | P0 |
| 设备编辑 | 修改设备信息 | P0 |
| 设备删除 | 删除设备（需确认） | P1 |
| 设备详情 | 查看设备完整信息 | P0 |
| 设备照片 | 上传和查看设备照片 | P1 |
| 设备参数 | 自定义设备参数和值 | P1 |

### 2.3 设备全生命周期

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 入库记录 | 记录设备入库信息 | P0 |
| 领用分配 | 记录设备领用/分配 | P1 |
| 安装记录 | 关联安装任务记录 | P1 |
| 维修记录 | 记录设备维修历史 | P1 |
| 报废记录 | 记录设备报废信息 | P1 |
| 生命周期时间轴 | 完整展示设备状态变更历史 | P0 |

### 2.4 设备状态管理

| 状态 | 说明 | 优先级 |
|------|------|--------|
| 库存 | 设备在仓库中 | P0 |
| 在安装 | 正在安装中 | P0 |
| 已安装 | 安装完成待启用 | P0 |
| 运行中 | 正常运行 | P0 |
| 维修中 | 正在维修 | P0 |
| 报废 | 已报废 | P0 |

## 3. 数据模型

### 3.1 设备类型表 (dev_device_type)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| parent_id | BIGINT | 父类型ID |
| name | VARCHAR(100) | 类型名称 |
| code | VARCHAR(50) | 类型编码 |
| remark | TEXT | 备注 |
| create_time | DATETIME | 创建时间 |

### 3.2 设备台账表 (dev_device)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| code | VARCHAR(50) | 设备编号 |
| name | VARCHAR(100) | 设备名称 |
| type_id | BIGINT | 设备类型ID |
| project_id | BIGINT | 所属项目ID |
| site_id | BIGINT | 所属站点ID |
| model | VARCHAR(100) | 设备型号 |
| specification | VARCHAR(200) | 规格 |
| manufacturer | VARCHAR(100) | 生产厂商 |
| status | VARCHAR(20) | 设备状态 |
| install_location | VARCHAR(200) | 安装位置 |
| purchase_date | DATE | 购买日期 |
| warranty_date | DATE | 保修到期日期 |
| remark | TEXT | 备注 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 3.3 设备参数表 (dev_device_param)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| device_id | BIGINT | 设备ID |
| param_name | VARCHAR(100) | 参数名称 |
| param_value | VARCHAR(200) | 参数值 |
| create_time | DATETIME | 创建时间 |

### 3.4 设备照片表 (dev_device_photo)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| device_id | BIGINT | 设备ID |
| photo_url | VARCHAR(500) | 照片URL |
| photo_type | VARCHAR(20) | 照片类型 |
| create_time | DATETIME | 上传时间 |

### 3.5 设备生命周期日志表 (dev_device_log)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| device_id | BIGINT | 设备ID |
| event_type | VARCHAR(50) | 事件类型 |
| from_status | VARCHAR(20) | 原状态 |
| to_status | VARCHAR(20) | 新状态 |
| event_time | DATETIME | 事件时间 |
| operator_id | BIGINT | 操作人ID |
| remark | TEXT | 备注 |
| create_time | DATETIME | 创建时间 |

## 4. API接口

### 4.1 设备类型接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/device-types | 类型列表（树形） |
| POST | /api/device-types | 创建类型 |
| PUT | /api/device-types/{id} | 更新类型 |
| DELETE | /api/device-types/{id} | 删除类型 |

### 4.2 设备接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/devices | 设备列表（分页+筛选） |
| POST | /api/devices | 创建设备 |
| GET | /api/devices/{id} | 设备详情 |
| PUT | /api/devices/{id} | 更新设备 |
| DELETE | /api/devices/{id} | 删除设备 |
| GET | /api/devices/{id}/lifecycle | 设备生命周期 |
| POST | /api/devices/{id}/status | 更新设备状态 |

### 4.3 设备照片接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/devices/{id}/photos | 设备照片列表 |
| POST | /api/devices/{id}/photos | 上传照片 |
| DELETE | /api/photos/{id} | 删除照片 |

## 5. 业务流程

### 5.1 设备入库流程

```
1. 选择设备类型
2. 填写设备基本信息（编号、名称、型号等）
3. 上传设备照片
4. 设置设备参数
5. 提交入库
6. 系统生成入库记录
7. 设备状态变更为"库存"
```

### 5.2 设备状态变更流程

```
1. 设备发生状态变更（领用、安装、维修、报废）
2. 系统记录变更事件
3. 更新设备当前状态
4. 记录操作人和时间
5. 在生命周期时间轴展示
```

## 6. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 设备类型 | /devices/types | 设备类型树 |
| 设备列表 | /devices | 设备表格展示 |
| 设备详情 | /devices/:id | 设备信息展示 |
| 设备表单 | /devices/form | 新增/编辑设备 |

## 7. 权限控制

| 角色 | 设备管理 | 设备查看 |
|------|----------|----------|
| 超级管理员 | 完全控制 | 是 |
| 项目管理员 | 完全控制 | 是 |
| 运维工程师 | 查看+编辑 | 是 |
| 现场人员 | 查看 | 是 |
