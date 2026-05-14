# 设备管理模块

## 1. 模块概述

设备管理模块负责管理企业所有设备资产，实现设备台账管理、设备安装（新增设备）、全生命周期追踪、设备档案、设备巡检、设备维护和故障管理等功能。设备是平台的核心业务对象，与项目、专题、维护公司等有密切关联。安装一台设备就是新增一台设备，安装时可以在弹出的地图上选择安装位置获取经纬度坐标。使用Redis缓存设备热点数据，MinIO存储设备相关的照片、附件等文件。

## 2. 用户故事

作为设备管理员，我需要创建设备台账（安装设备），点击新增设备后弹出表单。首先选择设备所属专题和型号，填写设备编码、IMEI号等基本信息。在位置信息填写时，点击地图图标弹出地图，可以在地图上点击选择安装位置，系统自动获取经纬度坐标，也可以手动输入经纬度。同时填写区县、街道、详细地址，设置点位名称，是否在窨井内。还可以上传设备照片和现场照片，填写现场情况说明。默认设备调试状态为待调试，调试后可以修改为已调试。设备状态允许用户修改，默认是离线，可以修改为OFFLINE-离线，ONLINE-在线，FAULT-故障，MAINTENANCE-维护中的一种。

我可以为设备制定巡检计划，系统自动生成巡检任务分配给巡检人员。巡检人员到达现场后按模板填写巡检记录、上传巡检照片。当设备出现问题时，我可以创建维护工单，分配给维护公司处理，工单完成后进行验收。现场人员发现故障时可上报故障，填写故障类型、级别和描述，上传现场照片，处理人分析原因并记录解决方案。

## 3. 功能清单

### 3.1 设备台账（设备安装）

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备列表 | 展示所有设备，支持分页、筛选、搜索 | P0 |
| 设备新增 | 创建设备台账 | P0 |
| 设备编辑 | 修改设备信息 | P0 |
| 设备删除 | 删除设备（需确认） | P1 |
| 设备详情 | 查看设备完整信息 | P0 |
| 设备导入导出 | Excel批量导入导出设备数据 | P1 |
| 设备筛选 | 按专题、设备类型、状态等筛选 | P0 |

### 3.2 设备档案

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备技术参数 | 查看和编辑设备技术参数 | P0 |
| 维保记录 | 查看设备维保历史记录 | P0 |
| 故障历史 | 查看设备故障历史 | P0 |
| 附件管理 | 设备相关附件上传下载 | P0 |
| 设备照片 | 设备照片、现场照片管理 | P0 |

### 3.3 设备巡检

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 巡检计划 | 巡检计划列表和配置（支持周期配置和执行人分配） | P0 |
| 巡检任务分配 | 巡检任务分配给执行人 | P0 |
| 巡检执行 | 执行巡检任务，填写巡检记录（按模板填写） | P0 |
| 巡检记录 | 巡检记录查询和详情 | P0 |
| 巡检统计 | 巡检完成率、异常率统计 | P1 |

### 3.4 设备维护

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 维护工单列表 | 设备维护工单列表 | P0 |
| 维护工单新增 | 创建设备维护工单 | P0 |
| 维护工单处理 | 处理工单，执行维护操作 | P0 |
| 维护工单验收 | 维护完成后验收确认 | P0 |
| 维护记录 | 维护历史记录查询 | P0 |

### 3.5 设备故障管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 故障上报 | 现场故障上报登记 | P0 |
| 故障处理 | 故障处理流程 | P0 |
| 故障分析 | 故障原因分析记录 | P0 |
| 故障统计 | 故障数量、类型、趋势统计 | P0 |
| 故障知识库 | 常见故障及解决方案 | P1 |

### 3.6 设备统计

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备分布图 | 地图上展示设备分布 | P0 |
| 设备状态统计 | 各状态设备数量统计 | P0 |
| 维保统计 | 维保计划执行统计 | P0 |
| 趋势分析 | 设备运行趋势分析 | P1 |

## 4. Redis缓存使用场景

| 场景 | 说明 |
|------|------|
| 设备详情缓存 | 缓存常用设备的详细信息，减少数据库查询 |
| 设备列表缓存 | 缓存设备列表查询结果，提升列表加载速度 |
| 设备统计数据缓存 | 缓存设备统计、状态分布等数据 |
| 设备位置信息缓存 | 缓存设备GPS位置信息，支持地图快速加载 |
| 字典数据缓存 | 缓存设备类型、状态等字典数据 |

## 5. MinIO对象存储使用场景

| 场景 | 说明 |
|------|------|
| 设备照片存储 | 存储设备照片、现场照片等图片文件 |
| 设备档案附件 | 存储设备的技术文档、维保记录等附件 |
| 巡检照片存储 | 存储巡检任务执行过程中拍摄的照片 |
| 故障现场照片 | 存储设备故障现场照片 |
| 工单附件存储 | 存储维护工单处理过程中的附件 |
| 导入导出文件 | 临时存储设备导入导出Excel文件 |

## 6. 设备信息字段说明

### 6.1 基础信息

| 字段名称 | 字段编码 | 类型 | 说明 |
|----------|----------|------|------|
| 专题代码 | topic_code | VARCHAR(50) | 设备所属专题编码 |
| 设备型号编码 | model_code | VARCHAR(50) | 设备型号编码 |
| 设备编码 | code | VARCHAR(50) | 设备唯一编码 |
| 第三方设备编码 | third_party_code | VARCHAR(50) | 第三方系统设备编码 |
| IMEI | imei | VARCHAR(20) | 设备IMEI号 |
| 设备所属公司 | company_name | VARCHAR(100) | 设备所属公司名称 |

### 6.2 位置信息

| 字段名称 | 字段编码 | 类型 | 说明 |
|----------|----------|------|------|
| 所属区县编码 | district_code | VARCHAR(20) | 设备所在区县行政区划编码 |
| 所属街道编码 | street_code | VARCHAR(20) | 设备所在街道行政区划编码 |
| 设备安装详细位置 | install_address | VARCHAR(200) | 设备安装详细地址 |
| 经度 | longitude | DECIMAL(10,6) | WGS84坐标系经度 |
| 纬度 | latitude | DECIMAL(10,6) | WGS84坐标系纬度 |
| 点位名称 | location_name | VARCHAR(100) | 设备安装点位名称 |
| 是否在窨井内 | in_well | SMALLINT | 是否在窨井内（0-否，1-是） |

### 6.3 安装信息

| 字段名称 | 字段编码 | 类型 | 说明 |
|----------|----------|------|------|
| 安装日期 | install_date | DATE | 设备安装日期 |
| 安装人员 | installer_name | VARCHAR(50) | 安装人员姓名 |
| 联系电话 | contact_phone | VARCHAR(20) | 联系电话 |

### 6.4 照片信息

| 字段名称 | 字段编码 | 类型 | 说明 |
|----------|----------|------|------|
| 设备照片列表 | device_photos | JSONB | 设备照片集合，存储MinIO文件URL |
| 现场照片列表 | site_photos | JSONB | 现场照片集合，存储MinIO文件URL |
| 现场情况说明 | site_description | TEXT | 现场情况文字说明 |

### 6.5 状态信息

| 字段名称 | 字段编码 | 类型 | 说明 |
|----------|----------|------|------|
| 调试状态 | debug_status | VARCHAR(20) | 调试状态（NOT_DEBUGGED-未调试，DEBUGGING-调试中，COMPLETED-已完成） |
| 设备状态 | status | VARCHAR(20) | 设备状态（OFFLINE-离线，ONLINE-在线，FAULT-故障，MAINTENANCE-维护中） |
| 最后在线时间 | last_online_time | TIMESTAMP | 最后在线时间 |
| 最后巡检时间 | last_inspection_time | TIMESTAMP | 最后巡检时间 |

## 7. 数据模型

### 7.1 设备表 (dev_device)

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
| topic_code | VARCHAR(50) | 专题编码 |
| model_code | VARCHAR(50) | 设备型号编码 |
| code | VARCHAR(50) | 设备编码（唯一） |
| third_party_code | VARCHAR(50) | 第三方设备编码 |
| imei | VARCHAR(20) | IMEI号 |
| company_name | VARCHAR(100) | 设备所属公司 |
| district_code | VARCHAR(20) | 所属区县编码 |
| street_code | VARCHAR(20) | 所属街道编码 |
| install_address | VARCHAR(200) | 设备安装详细位置 |
| longitude | DECIMAL(10,6) | 经度 |
| latitude | DECIMAL(10,6) | 纬度 |
| location_name | VARCHAR(100) | 点位名称 |
| in_well | SMALLINT | 是否在窨井内（0-否，1-是） |
| install_date | DATE | 安装日期 |
| installer_name | VARCHAR(50) | 安装人员 |
| contact_phone | VARCHAR(20) | 联系电话 |
| device_photos | JSONB | 设备照片列表，存储MinIO文件URL |
| site_photos | JSONB | 现场照片列表，存储MinIO文件URL |
| site_description | TEXT | 现场情况说明 |
| debug_status | VARCHAR(20) | 调试状态 |
| status | VARCHAR(20) | 设备状态 |
| last_online_time | TIMESTAMP | 最后在线时间 |
| last_inspection_time | TIMESTAMP | 最后巡检时间 |

### 7.2 设备参数表 (dev_device_param)

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
| device_id | BIGINT | 设备ID |
| param_name | VARCHAR(100) | 参数名称 |
| param_value | VARCHAR(200) | 参数值 |
| param_unit | VARCHAR(20) | 参数单位 |

### 7.3 设备档案附件表 (dev_device_attachment)

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
| device_id | BIGINT | 设备ID |
| name | VARCHAR(100) | 附件名称 |
| file_url | VARCHAR(500) | 文件URL（MinIO存储路径） |
| file_type | VARCHAR(20) | 文件类型 |
| file_size | BIGINT | 文件大小 |
| attachment_type | VARCHAR(20) | 附件类型（TECHNICAL-技术资料，MAINTENANCE-维保资料，OTHER-其他） |

### 7.4 设备生命周期日志表 (dev_device_log)

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
| device_id | BIGINT | 设备ID |
| event_type | VARCHAR(50) | 事件类型（INSTALL-安装，DEBUG-调试，INSPECTION-巡检，MAINTENANCE-维护，FAULT-故障，STATUS_CHANGE-状态变更） |
| from_status | VARCHAR(20) | 原状态 |
| to_status | VARCHAR(20) | 新状态 |
| event_time | TIMESTAMP | 事件时间 |
| operator_id | BIGINT | 操作人ID |
| description | TEXT | 事件描述 |

### 7.5 巡检计划表 (dev_inspection_plan)

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
| project_id | BIGINT | 项目ID |
| name | VARCHAR(100) | 计划名称 |
| start_time | TIMESTAMP | 开始时间 |
| end_time | TIMESTAMP | 结束时间 |
| enabled_status | VARCHAR(20) | 启用状态（ENABLED-启用，DISABLED-停用） |
| completed_status | VARCHAR(20) | 完成状态（IN_PROGRESS-进行中，COMPLETED-已完成） |

### 7.6 巡检计划设备关联表 (dev_inspection_plan_device)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| plan_id | BIGINT | 巡检计划ID |
| device_id | BIGINT | 设备ID |

### 7.7 巡检计划人员关联表 (dev_inspection_plan_executor)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| plan_id | BIGINT | 巡检计划ID |
| executor_id | BIGINT | 巡检人员ID |

### 7.8 巡检任务表 (dev_inspection_task)

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
| plan_id | BIGINT | 巡检计划ID |
| device_id | BIGINT | 设备ID |
| executor_id | BIGINT | 执行人ID |
| plan_time | TIMESTAMP | 计划执行时间 |
| execute_time | TIMESTAMP | 实际执行时间 |
| status | VARCHAR(20) | 状态（PENDING-待执行，COMPLETED-已完成，OVERDUE-已逾期） |

### 7.7 巡检记录表 (dev_inspection_record)

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
| task_id | BIGINT | 巡检任务ID |
| device_id | BIGINT | 设备ID |
| executor_id | BIGINT | 执行人ID |
| execute_time | TIMESTAMP | 执行时间 |
| result_data | JSONB | 巡检结果数据 |
| photos | JSONB | 巡检照片，存储MinIO文件URL |
| status | VARCHAR(20) | 巡检状态（NORMAL-正常，ABNORMAL-异常） |
| description | TEXT | 巡检说明

### 7.8 维护工单表 (dev_maintenance_order)

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
| project_id | BIGINT | 项目ID |
| device_id | BIGINT | 设备ID |
| order_no | VARCHAR(50) | 工单编号 |
| title | VARCHAR(100) | 工单标题 |
| description | TEXT | 工单描述 |
| maintenance_company_id | BIGINT | 维护公司ID |
| handler_id | BIGINT | 处理人ID |
| priority | VARCHAR(20) | 优先级（LOW-低，MEDIUM-中，HIGH-高，URGENT-紧急） |
| status | VARCHAR(20) | 状态（PENDING-待处理，PROCESSING-处理中，COMPLETED-已完成，ACCEPTED-已验收，CANCELLED-已取消） |
| assign_time | TIMESTAMP | 分配时间 |
| process_time | TIMESTAMP | 处理时间 |
| complete_time | TIMESTAMP | 完成时间 |
| accept_time | TIMESTAMP | 验收时间 |

### 7.9 工单处理记录表 (dev_order_record)

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
| order_id | BIGINT | 工单ID |
| operator_id | BIGINT | 操作人ID |
| action | VARCHAR(50) | 操作类型（CREATE-创建，ASSIGN-分配，PROCESS-处理，COMPLETE-完成，ACCEPT-验收，CANCEL-取消） |
| description | TEXT | 操作描述 |
| attachments | JSONB | 附件，存储MinIO文件URL |

### 7.10 故障记录表 (dev_fault_record)

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
| device_id | BIGINT | 设备ID |
| fault_no | VARCHAR(50) | 故障编号 |
| title | VARCHAR(100) | 故障标题 |
| fault_type | VARCHAR(50) | 故障类型 |
| fault_level | VARCHAR(20) | 故障级别（LOW-轻微，MEDIUM-一般，HIGH-严重，CRITICAL-致命） |
| description | TEXT | 故障描述 |
| reporter_id | BIGINT | 上报人ID |
| reporter_time | TIMESTAMP | 上报时间 |
| handler_id | BIGINT | 处理人ID |
| handle_time | TIMESTAMP | 处理时间 |
| fault_reason | TEXT | 故障原因 |
| solution | TEXT | 解决方案 |
| status | VARCHAR(20) | 状态（REPORTED-已上报，PROCESSING-处理中，RESOLVED-已解决，CLOSED-已关闭） |
| resolve_time | TIMESTAMP | 解决时间 |
| photos | JSONB | 故障现场照片，存储MinIO文件URL |

## 8. API接口

### 8.1 设备台账（设备安装）接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/devices | 设备列表（分页+筛选+搜索） |
| POST | /api/devices | 创建设备 |
| GET | /api/devices/{id} | 设备详情 |
| PUT | /api/devices/{id} | 更新设备 |
| DELETE | /api/devices/{id} | 删除设备 |
| GET | /api/devices/export | 导出设备数据 |
| POST | /api/devices/import | 导入设备数据 |
| GET | /api/devices/{id}/lifecycle | 设备生命周期 |
| PUT | /api/devices/{id}/status | 更新设备状态 |
| POST | /api/devices/{id}/photos | 上传设备照片到MinIO |
| POST | /api/devices/{id}/site-photos | 上传现场照片到MinIO |

### 8.2 设备档案接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/devices/{id}/params | 设备参数列表 |
| PUT | /api/devices/{id}/params | 更新设备参数 |
| GET | /api/devices/{id}/attachments | 设备附件列表 |
| POST | /api/devices/{id}/attachments | 上传设备附件到MinIO |
| DELETE | /api/devices/{id}/attachments/{attachmentId} | 删除附件 |
| GET | /api/devices/{id}/maintenance-records | 维保记录 |
| GET | /api/devices/{id}/fault-records | 故障历史 |

### 8.3 巡检接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/inspection/plans | 巡检计划列表 |
| POST | /api/inspection/plans | 创建巡检计划 |
| PUT | /api/inspection/plans/{id} | 更新巡检计划 |
| DELETE | /api/inspection/plans/{id} | 删除巡检计划 |
| GET | /api/inspection/tasks | 巡检任务列表 |
| POST | /api/inspection/tasks/{id}/execute | 执行巡检任务 |
| GET | /api/inspection/records | 巡检记录列表 |
| GET | /api/inspection/records/{id} | 巡检记录详情 |
| GET | /api/inspection/stats | 巡检统计 |

### 8.4 维护工单接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/maintenance/orders | 工单列表 |
| POST | /api/maintenance/orders | 创建工单 |
| GET | /api/maintenance/orders/{id} | 工单详情 |
| PUT | /api/maintenance/orders/{id} | 更新工单 |
| PUT | /api/maintenance/orders/{id}/assign | 分配工单 |
| PUT | /api/maintenance/orders/{id}/process | 处理工单 |
| PUT | /api/maintenance/orders/{id}/complete | 完成工单 |
| PUT | /api/maintenance/orders/{id}/accept | 验收工单 |
| PUT | /api/maintenance/orders/{id}/cancel | 取消工单 |
| GET | /api/maintenance/orders/{id}/records | 工单处理记录 |
| POST | /api/maintenance/orders/{id}/attachments | 上传工单附件到MinIO |

### 8.5 故障管理接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/faults | 故障列表 |
| POST | /api/faults | 上报故障 |
| GET | /api/faults/{id} | 故障详情 |
| PUT | /api/faults/{id} | 更新故障 |
| PUT | /api/faults/{id}/handle | 处理故障 |
| PUT | /api/faults/{id}/resolve | 解决故障 |
| PUT | /api/faults/{id}/close | 关闭故障 |
| GET | /api/faults/stats | 故障统计 |
| GET | /api/faults/knowledge | 故障知识库 |
| POST | /api/faults/{id}/photos | 上传故障现场照片到MinIO |

### 8.6 设备统计接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/devices/stats/overview | 设备统计概览 |
| GET | /api/devices/stats/status | 设备状态统计 |
| GET | /api/devices/stats/distribution | 设备分布统计 |
| GET | /api/devices/stats/trend | 设备趋势统计 |
| GET | /api/devices/map | 设备地图数据 |

## 9. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 设备台账 | /devices | 设备列表 |
| 设备详情 | /devices/:id | 设备详情 |
| 设备表单 | /devices/form | 新增/编辑设备 |
| 设备档案 | /devices/:id/archive | 设备档案 |
| 巡检计划 | /devices/inspection/plans | 巡检计划 |
| 巡检任务 | /devices/inspection/tasks | 巡检任务 |
| 巡检记录 | /devices/inspection/records | 巡检记录 |
| 维护工单 | /devices/maintenance/orders | 维护工单 |
| 工单详情 | /devices/maintenance/orders/:id | 工单详情 |
| 故障管理 | /devices/faults | 故障列表 |
| 故障详情 | /devices/faults/:id | 故障详情 |
| 设备统计 | /devices/stats | 设备统计 |

## 10. 业务流程

### 10.1 设备安装流程（新增设备）

```
1. 点击"新增设备"按钮，弹出设备安装表单
2. 选择设备所属专题和设备型号
3. 填写设备基本信息（编码、第三方编码、IMEI、公司名称等）
4. 填写位置信息：
   - 选择区县、街道
   - 填写详细安装地址
   - 点击地图图标弹出地图
   - 在地图上点击选择安装位置，系统自动获取经纬度
   - 也可以手动输入经纬度坐标
5. 设置点位名称，是否在窨井内
6. 设置安装日期和安装人员信息
7. 上传设备照片和现场照片（存储到MinIO）
8. 填写现场情况说明
9. 提交安装，设备入库，状态为"离线"
10. 如需调试，进入设备调试流程
```

### 10.2 设备调试流程

```
1. 设备安装完成后，进入调试流程
2. 调试状态：未调试 -> 调试中 -> 已完成
3. 填写调试信息
4. 调试完成后，设备状态变为"在线"
```

### 10.3 巡检执行流程

```
1. 系统根据巡检计划自动生成巡检任务
2. 巡检任务分配给执行人
3. 执行人到达现场，执行巡检
4. 填写巡检记录（按巡检模板填写各项）
5. 上传巡检照片
6. 提交巡检结果
7. 系统更新设备最后巡检时间
```

### 10.4 维护工单流程

```
1. 发现设备问题，创建维护工单
2. 选择设备，填写问题描述
3. 设置优先级
4. 分配给维护公司/处理人
5. 处理人接收工单，开始处理
6. 处理完成后提交处理结果
7. 工单发起人验收
8. 验收通过，工单关闭
```

### 10.5 故障处理流程

```
1. 设备发生故障，现场人员上报
2. 选择故障类型，设置故障级别
3. 填写故障描述，上传故障照片
4. 故障分配给处理人
5. 处理人分析故障原因
6. 填写故障原因和解决方案
7. 处理完成后标记为已解决
8. 记录到故障知识库
```

## 11. 验证标准

### 8.1 单元测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 设备列表查询 | 分页、搜索、多条件筛选 | 正确返回结果 | P0 |
| 设备新增 | 数据验证、必填项、编码唯一性 | 新增成功、验证正确 | P0 |
| 设备编辑 | 更新功能、数据一致性 | 更新成功、数据正确 | P0 |
| 设备状态变更 | 状态变更、日志记录 | 状态变更、日志正确 | P0 |
| 生命周期查询 | 生命周期时间轴 | 时间轴数据完整 | P0 |
| 巡检计划 | 计划生成、周期执行 | 计划正确生成 | P0 |
| 巡检执行 | 巡检记录、结果保存 | 记录正确保存 | P0 |
| 维护工单 | 工单CRUD、状态流转 | 流转正确 | P0 |
| 故障管理 | 故障上报、处理、统计 | 功能正常 | P0 |
| 设备统计 | 统计数据准确性 | 数据准确 | P0 |

### 8.2 集成测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 设备编号唯一 | 设备编号唯一性验证 | 重复编号报错 | P0 |
| 文件上传 | 设备照片上传 | 上传成功、可访问 | P1 |
| 跨模块关联 | 设备与项目、专题关联 | 数据隔离正确 | P0 |
| 事务回滚 | 异常情况下回滚 | 数据一致性保持 | P0 |
| 巡检任务生成 | 计划任务自动生成 | 按时生成任务 | P0 |

### 8.3 UI交互验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 状态标签 | 设备状态标签样式 | 正确区分不同状态 | P0 |
| 设备地图 | 设备分布地图展示 | 地图加载正常 | P0 |
| 巡检表单 | 巡检项表单动态渲染 | 表单正确渲染 | P0 |
| 工单流程 | 工单状态流转展示 | 状态清晰可见 | P0 |
| 故障登记 | 故障上报表单 | 表单验证正确 | P0 |

### 8.4 性能验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 大量设备数据 | 1000台设备数据 | 列表渲染流畅 | P0 |
| 图片加载 | 多张设备照片 | 加载不阻塞 | P1 |
| 地图性能 | 1000个设备标记 | 地图渲染流畅 | P1 |

### 8.5 验收测试用例

#### 测试用例 01: 设备入库

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 点击"新增设备" | 打开设备表单 |
| 2 | 选择专题"排水管网监测" | 专题选择成功 |
| 3 | 选择设备型号 | 型号关联显示类型和厂商 |
| 4 | 填写设备编码、第三方编码、IMEI | 信息正确保存 |
| 5 | 选择区县、街道，填写详细地址 | 位置信息正确 |
| 6 | 在地图上标记设备位置 | 经纬度正确获取 |
| 7 | 填写点位名称，选择是否在窨井内 | 选择正确保存 |
| 8 | 上传设备照片和现场照片 | 上传成功显示预览 |
| 9 | 保存设备 | 设备保存成功，状态为"离线" |

#### 测试用例 02: 设备调试

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 进入设备详情页 | 显示设备信息 |
| 2 | 点击"开始调试" | 调试状态变为"调试中" |
| 3 | 填写调试记录 | 调试记录保存 |
| 4 | 点击"调试完成" | 调试状态变为"已完成"，设备状态变为"在线" |
| 5 | 查看设备生命周期 | 显示调试记录 |

#### 测试用例 03: 巡检执行

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 进入巡检任务列表 | 显示待执行的巡检任务 |
| 2 | 选择任务，点击"执行巡检" | 打开巡检表单 |
| 3 | 按巡检模板填写巡检项 | 巡检项正确加载 |
| 4 | 输入巡检数值，上传巡检照片 | 照片上传成功 |
| 5 | 提交巡检结果 | 任务状态变为"已完成" |
| 6 | 查看设备最后巡检时间 | 时间已更新 |

#### 测试用例 04: 维护工单处理

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 发现设备问题，点击"创建工单" | 打开工单表单 |
| 2 | 选择设备，填写问题描述 | 信息正确保存 |
| 3 | 设置优先级为"紧急" | 优先级设置成功 |
| 4 | 分配给维护公司A | 工单分配成功 |
| 5 | 维护公司接收工单 | 状态变为"处理中" |
| 6 | 处理完成后点击"完成" | 处理记录保存，状态变为"已完成" |
| 7 | 工单发起人验收通过 | 工单状态变为"已验收" |
| 8 | 查看工单处理记录 | 完整记录显示 |

#### 测试用例 05: 故障上报与处理

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 设备发生故障，点击"上报故障" | 打开故障上报表单 |
| 2 | 选择设备，填写故障标题 | 信息正确保存 |
| 3 | 选择故障类型"通信故障"，故障级别"严重" | 选择成功 |
| 4 | 填写故障描述，上传故障现场照片 | 描述和照片保存 |
| 5 | 提交故障 | 故障状态为"已上报" |
| 6 | 故障处理人分析原因，填写故障原因和解决方案 | 原因和方案保存 |
| 7 | 点击"解决故障" | 状态变为"已解决" |
| 8 | 故障关闭，查看故障统计 | 统计数据更新 |
