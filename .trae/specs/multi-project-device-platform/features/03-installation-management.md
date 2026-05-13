# 安装管理模块

## 1. 模块概述

安装管理模块负责管理设备安装全过程，包括安装任务分配、现场管理、检查清单和安装文档管理。

## 2. 功能清单

### 2.1 安装任务管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 任务列表 | 展示所有安装任务，支持筛选 | P0 |
| 任务创建 | 创建新的安装任务 | P0 |
| 任务分配 | 分配安装人员 | P0 |
| 任务执行 | 执行安装任务 | P0 |
| 任务验收 | 安装完成验收 | P0 |
| 任务详情 | 查看任务完整信息 | P0 |
| 进度跟踪 | 展示安装进度 | P1 |

### 2.2 安装现场管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 位置确认 | 确认安装位置 | P0 |
| 条件检查 | 安装条件检查清单 | P0 |
| 过程记录 | 安装过程文字记录 | P0 |
| 照片记录 | 安装过程照片上传 | P1 |
| 问题反馈 | 安装问题记录和反馈 | P0 |
| 材料使用 | 安装材料使用记录 | P1 |
| 电子签名 | 安装验收电子签名 | P0 |

### 2.3 安装文档管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 手册关联 | 关联设备安装手册 | P1 |
| 验收标准 | 安装验收标准文档 | P1 |
| 报告生成 | 自动生成安装完成报告 | P1 |
| 档案归档 | 安装档案归档管理 | P1 |

### 2.4 安装任务状态

| 状态 | 说明 |
|------|------|
| 待分配 | 任务创建待分配人员 |
| 执行中 | 安装进行中 |
| 待验收 | 安装完成待验收 |
| 已完成 | 验收通过完成 |
| 已取消 | 任务取消 |

## 3. 数据模型

### 3.1 安装任务表 (ins_task)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_no | VARCHAR(50) | 任务编号 |
| project_id | BIGINT | 项目ID |
| device_id | BIGINT | 设备ID |
| site_id | BIGINT | 安装站点ID |
| title | VARCHAR(200) | 任务标题 |
| description | TEXT | 任务描述 |
| status | VARCHAR(20) | 任务状态 |
| priority | VARCHAR(20) | 优先级 |
| plan_start_date | DATETIME | 计划开始时间 |
| plan_end_date | DATETIME | 计划结束时间 |
| actual_start_date | DATETIME | 实际开始时间 |
| actual_end_date | DATETIME | 实际结束时间 |
| assigner_id | BIGINT | 指派人ID |
| executor_id | BIGINT | 执行人ID |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 3.2 安装记录表 (ins_record)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_id | BIGINT | 任务ID |
| record_type | VARCHAR(50) | 记录类型 |
| content | TEXT | 记录内容 |
| operator_id | BIGINT | 操作人ID |
| create_time | DATETIME | 创建时间 |

### 3.3 安装检查项表 (ins_checklist)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_id | BIGINT | 任务ID |
| item_name | VARCHAR(100) | 检查项名称 |
| item_type | VARCHAR(50) | 检查项类型 |
| is_required | TINYINT | 是否必检 |
| check_result | VARCHAR(20) | 检查结果 |
| remark | TEXT | 备注 |
| create_time | DATETIME | 创建时间 |

### 3.4 安装文档表 (ins_document)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| task_id | BIGINT | 任务ID |
| doc_type | VARCHAR(50) | 文档类型 |
| doc_name | VARCHAR(100) | 文档名称 |
| doc_url | VARCHAR(500) | 文档URL |
| create_time | DATETIME | 上传时间 |

## 4. API接口

### 4.1 安装任务接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/installations/tasks | 任务列表（分页+筛选） |
| POST | /api/installations/tasks | 创建任务 |
| GET | /api/installations/tasks/{id} | 任务详情 |
| PUT | /api/installations/tasks/{id} | 更新任务 |
| DELETE | /api/installations/tasks/{id} | 删除任务 |
| POST | /api/installations/tasks/{id}/assign | 分配任务 |
| POST | /api/installations/tasks/{id}/start | 开始任务 |
| POST | /api/installations/tasks/{id}/complete | 完成任务 |
| POST | /api/installations/tasks/{id}/cancel | 取消任务 |

### 4.2 安装记录接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/installations/tasks/{taskId}/records | 记录列表 |
| POST | /api/installations/tasks/{taskId}/records | 添加记录 |
| GET | /api/installations/tasks/{taskId}/checklist | 检查清单 |
| PUT | /api/installations/tasks/{taskId}/checklist | 更新检查结果 |

### 4.3 安装文档接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/installations/tasks/{taskId}/documents | 文档列表 |
| POST | /api/installations/tasks/{taskId}/documents | 上传文档 |
| DELETE | /api/installations/documents/{id} | 删除文档 |

## 5. 业务流程

### 5.1 安装任务执行流程

```
1. 创建安装任务，选择设备和安装位置
2. 设置计划时间，分配执行人员
3. 执行人员开始任务，记录开始时间
4. 现场执行安装，完成检查清单
5. 上传安装过程照片
6. 执行人员完成任务申请验收
7. 验收人员现场验收
8. 验收通过，电子签名确认
9. 任务完成，设备状态更新
```

### 5.2 安装检查流程

```
1. 进入安装执行页面
2. 系统展示安装检查清单
3. 执行人员逐项检查
4. 记录每项检查结果（通过/不通过/不适用）
5. 不通过项记录问题描述
6. 全部必检项通过后可提交验收
```

## 6. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 安装任务列表 | /installations/tasks | 任务看板/列表 |
| 安装任务详情 | /installations/tasks/:id | 任务详情 |
| 安装执行 | /installations/execute/:id | 执行安装任务 |
| 安装统计 | /installations/stats | 安装统计 |

## 7. 权限控制

| 角色 | 安装任务管理 | 安装执行 | 安装验收 |
|------|--------------|----------|----------|
| 超级管理员 | 完全控制 | 是 | 是 |
| 项目管理员 | 完全控制 | 是 | 是 |
| 运维工程师 | 查看+创建 | 是 | 否 |
| 现场人员 | 查看 | 是 | 否 |
