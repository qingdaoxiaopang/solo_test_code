# 运维管理模块

## 1. 模块概述

运维管理模块负责管理设备运维全流程，包括运维工单处理、巡检计划执行、设备维修和备件管理。

## 2. 用户故事

作为运维管理员，当发现设备问题时我可以创建运维工单，选择工单类型（巡检、维护、故障报修、紧急维修），设置优先级，填写问题描述和位置信息。管理员将工单派发给维修人员，维修人员接单后开始处理，记录处理过程，完成后提交验收。维修过程中如需使用备件，可进行备件出库操作，系统自动扣减库存。我还可以制定巡检计划，设置巡检周期和路线，系统自动生成巡检任务。巡检人员按路线执行巡检，记录检查项结果和参数值，发现异常及时记录。备件库存不足时系统自动预警，提醒及时补货。

## 3. 功能清单

### 3.1 运维工单管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 工单列表 | 展示所有工单，支持筛选排序 | P0 |
| 工单创建 | 创建新的运维工单 | P0 |
| 工单派单 | 派发给维修人员 | P0 |
| 工单接单 | 维修人员接单 | P0 |
| 工单处理 | 处理工单问题 | P0 |
| 工单完成 | 工单处理完成验收 | P0 |
| 工单评价 | 工单满意度评价 | P2 |
| 工单详情 | 查看工单完整信息 | P0 |
| 处理时间轴 | 展示工单处理历史 | P0 |

### 2.2 工单类型

| 类型 | 说明 |
|------|------|
| 巡检 | 定期巡检任务 |
| 维护 | 日常维护保养 |
| 故障报修 | 设备故障报修 |
| 紧急维修 | 紧急故障维修 |

### 2.3 工单优先级

| 优先级 | 说明 |
|--------|------|
| 紧急 | 需要立即处理 |
| 高 | 24小时内处理 |
| 中 | 3天内处理 |
| 低 | 一周内处理 |

### 2.4 工单状态

| 状态 | 说明 |
|------|------|
| 待派单 | 工单创建待派发 |
| 待接单 | 已派发等待接单 |
| 处理中 | 维修人员处理中 |
| 待验收 | 处理完成待验收 |
| 已完成 | 验收通过完成 |
| 已取消 | 工单取消 |

### 3.5 巡检管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 巡检计划列表 | 展示巡检计划 | P0 |
| 巡检计划创建 | 创建新的巡检计划 | P0 |
| 巡检周期设置 | 设置巡检周期 | P0 |
| 巡检路线配置 | 配置巡检路线 | P1 |
| 巡检任务生成 | 自动生成巡检任务 | P0 |
| 巡检执行 | 执行巡检任务 | P0 |
| 巡检项检查 | 设备状态和参数检查 | P0 |
| 巡检异常记录 | 记录巡检异常 | P0 |
| 巡检报告 | 生成巡检报告 | P1 |

### 2.6 设备维修管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 故障报修 | 登记设备故障 | P0 |
| 维修派工 | 派发给维修人员 | P0 |
| 维修过程记录 | 记录维修过程 | P0 |
| 材料使用 | 维修材料使用记录 | P1 |
| 维修验收 | 维修完成验收 | P0 |
| 维修费用 | 维修费用统计 | P1 |

### 3.7 备件管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 备件列表 | 备件库存列表 | P0 |
| 备件新增 | 新增备件 | P0 |
| 备件编辑 | 编辑备件信息 | P0 |
| 备件入库 | 备件入库操作 | P0 |
| 备件出库 | 备件出库使用 | P0 |
| 库存查询 | 库存数量查询 | P0 |
| 库存预警 | 库存不足预警 | P1 |
| 使用统计 | 备件使用统计 | P1 |

## 4. 数据模型

### 3.1 运维工单表 (opr_work_order)

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
| order_no | VARCHAR(50) | 工单编号 |
| project_id | BIGINT | 项目ID |
| device_id | BIGINT | 设备ID |
| type | VARCHAR(20) | 工单类型 |
| title | VARCHAR(200) | 工单标题 |
| description | TEXT | 问题描述 |
| priority | VARCHAR(20) | 优先级 |
| status | VARCHAR(20) | 工单状态 |
| reporter_id | BIGINT | 报修人ID |
| assignee_id | BIGINT | 指派人ID |
| handler_id | BIGINT | 处理人ID |
| location | VARCHAR(200) | 故障位置 |
| photos | JSONB | 照片，存储MinIO文件URL |
| plan_date | DATE | 计划处理日期 |
| complete_date | TIMESTAMP | 完成时间 |
| evaluation | VARCHAR(20) | 满意度评价 |

### 3.2 工单处理记录表 (opr_work_order_record)

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
| order_id | BIGINT | 工单ID |
| action | VARCHAR(50) | 操作类型 |
| content | TEXT | 处理内容 |
| operator_id | BIGINT | 操作人ID |

### 4.3 巡检计划表 (opr_inspection_plan)

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
| plan_no | VARCHAR(50) | 计划编号 |
| project_id | BIGINT | 项目ID |
| name | VARCHAR(100) | 计划名称 |
| cycle_type | VARCHAR(20) | 周期类型 |
| cycle_value | VARCHAR(50) | 周期值 |
| start_date | DATE | 开始日期 |
| end_date | DATE | 结束日期 |
| route_config | JSONB | 路线配置 |
| status | VARCHAR(20) | 状态 |

### 3.4 巡检任务表 (opr_inspection_task)

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
| plan_id | BIGINT | 计划ID |
| task_no | VARCHAR(50) | 任务编号 |
| project_id | BIGINT | 项目ID |
| executor_id | BIGINT | 执行人ID |
| plan_date | DATE | 计划日期 |
| actual_date | DATE | 实际日期 |
| status | VARCHAR(20) | 状态 |

### 4.5 巡检项表 (opr_inspection_item)

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
| task_id | BIGINT | 任务ID |
| device_id | BIGINT | 设备ID |
| item_name | VARCHAR(100) | 检查项名称 |
| check_standard | TEXT | 检查标准 |
| check_result | VARCHAR(20) | 检查结果 |
| param_value | VARCHAR(100) | 参数值 |

### 3.6 备件表 (opr_spare_part)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 备件编码 |
| name | VARCHAR(100) | 备件名称 |
| spec | VARCHAR(100) | 规格型号 |
| unit | VARCHAR(20) | 单位 |
| stock | INT | 当前库存 |
| min_stock | INT | 最低库存 |
| price | DECIMAL(10,2) | 单价 |

### 4.7 备件使用记录表 (opr_spare_part_record)

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
| project_id | BIGINT | 所属项目ID |
| part_id | BIGINT | 备件ID |
| order_id | BIGINT | 关联工单ID |
| type | VARCHAR(20) | 类型（入库/出库） |
| quantity | INT | 数量 |
| operator_id | BIGINT | 操作人ID |

## 5. API接口

### 4.1 工单接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/work-orders | 工单列表（分页+筛选） |
| POST | /api/work-orders | 创建工单 |
| GET | /api/work-orders/{id} | 工单详情 |
| PUT | /api/work-orders/{id} | 更新工单 |
| DELETE | /api/work-orders/{id} | 删除工单 |
| POST | /api/work-orders/{id}/dispatch | 派单 |
| POST | /api/work-orders/{id}/accept | 接单 |
| POST | /api/work-orders/{id}/handle | 处理工单 |
| POST | /api/work-orders/{id}/complete | 完成工单 |
| POST | /api/work-orders/{id}/cancel | 取消工单 |
| GET | /api/work-orders/{id}/records | 处理记录 |

### 5.2 巡检接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/inspections/plans | 巡检计划列表 |
| POST | /api/inspections/plans | 创建巡检计划 |
| GET | /api/inspections/plans/{id} | 计划详情 |
| PUT | /api/inspections/plans/{id} | 更新计划 |
| DELETE | /api/inspections/plans/{id} | 删除计划 |
| GET | /api/inspections/tasks | 巡检任务列表 |
| POST | /api/inspections/generate | 生成巡检任务 |
| GET | /api/inspections/tasks/{id} | 任务详情 |
| POST | /api/inspections/tasks/{id}/execute | 执行巡检 |
| PUT | /api/inspections/items/{id} | 更新检查项 |

### 4.3 备件接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/spare-parts | 备件列表 |
| POST | /api/spare-parts | 新增备件 |
| PUT | /api/spare-parts/{id} | 更新备件 |
| DELETE | /api/spare-parts/{id} | 删除备件 |
| POST | /api/spare-parts/{id}/stock-in | 入库 |
| POST | /api/spare-parts/{id}/stock-out | 出库 |
| GET | /api/spare-parts/{id}/records | 使用记录 |
| GET | /api/spare-parts/warnings | 库存预警 |

## 6. 业务流程

### 6.1 工单处理流程

```
1. 用户发现设备问题，创建工单
2. 工单状态：待派单
3. 管理员派单给维修人员
4. 工单状态：待接单
5. 维修人员接单
6. 工单状态：处理中
7. 维修人员处理问题
8. 维修人员完成任务
9. 工单状态：待验收
10. 管理员验收通过
11. 工单状态：已完成
12. 可选：用户评价
```

### 5.2 巡检执行流程

```
1. 系统根据巡检计划自动生成巡检任务
2. 巡检人员接收任务
3. 按照巡检路线执行巡检
4. 对每个巡检点进行检查
5. 记录检查结果和参数值
6. 发现异常记录异常
7. 巡检任务完成
8. 系统汇总巡检报告
```

## 7. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 工单列表 | /operations/work-orders | 工单列表 |
| 工单详情 | /operations/work-orders/:id | 工单详情 |
| 移动端工单 | /m/work-orders | 移动端工单 |
| 巡检计划 | /operations/inspections/plans | 巡检计划 |
| 巡检任务 | /operations/inspections/tasks | 巡检任务 |
| 移动端巡检 | /m/inspections | 移动端巡检 |
| 备件管理 | /operations/spare-parts | 备件列表 |
| 备件出入库 | /operations/spare-parts/stock | 备件出入库 |

## 7. 验证标准

### 8.1 单元测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 工单CRUD | 工单增删改查 | 功能正常 | P0 |
| 工单状态流转 | 工单状态转换 | 状态流转符合规则 | P0 |
| 库存计算 | 备件库存出入库 | 库存数量计算正确 | P0 |
| 巡检任务生成 | 按计划生成任务 | 任务正确生成 | P0 |

### 8.2 集成测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 设备工单关联 | 工单与设备关联 | 关联关系正确 | P0 |
| 库存预警 | 库存低于阈值 | 预警消息正确触发 | P1 |
| 消息通知 | 工单状态变更通知 | 通知正确发送 | P1 |

### 8.4 UI交互验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 工单时间轴 | 处理记录时间轴 | 时间线展示正确 | P0 |
| 优先级标识 | 紧急工单高亮显示 | 正确标识优先级 | P0 |
| 移动端工单 | 移动端快捷操作 | 操作流畅易用 | P1 |

### 8.4 验收测试用例

#### 测试用例 01: 完整工单流程

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 创建故障报修工单 | 工单创建成功 |
| 2 | 管理员派单给工程师 | 状态变为待接单 |
| 3 | 工程师接单 | 状态变为处理中 |
| 4 | 工程师填写处理记录 | 记录正确保存 |
| 5 | 使用备件出库 | 库存数量减少 |
| 6 | 工程师完成任务 | 状态变为待验收 |
| 7 | 管理员验收通过 | 工单完成 |

#### 测试用例 02: 巡检任务执行

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 创建巡检计划 | 计划创建成功 |
| 2 | 生成巡检任务 | 任务自动生成 |
| 3 | 执行人查看任务 | 任务详情正确 |
| 4 | 执行巡检检查项 | 检查结果正确保存 |
| 5 | 记录异常信息 | 异常正确记录 |
| 6 | 完成巡检任务 | 任务状态更新 |

