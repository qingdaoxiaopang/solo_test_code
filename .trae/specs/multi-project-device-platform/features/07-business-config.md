# 业务配置模块

## 1. 模块概述

业务配置模块负责管理平台业务层面的各类配置，包括设备类型、设备厂商、设备型号、专题、维护公司、维护项、巡检模板、备件库等。**业务配置按项目隔离**，每个项目有独立的配置数据，确保不同项目之间的业务配置互不影响。

## 2. 用户故事

作为业务配置管理员，我需要为项目配置设备类型，创建树形结构的设备类型层级并设置唯一编码，为每种设备类型关联可执行的维护项。我可以管理设备厂商信息，创建设备型号并关联到对应的类型和厂商。专题管理方面，我创建专题后配置该专题可管理的设备类型，再为用户分配专题访问权限，用户登录后只能看到授权的专题数据。维护公司管理包括创建公司并配置可维护的设备型号。我还能创建维护项模板，设置维护周期和作业标准，以及创建巡检模板配置巡检项和巡检周期。备件库管理涵盖备件分类、库存管理和备件申请审批流程。

## 3. 功能清单

### 2.1 设备类型管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备类型树 | 以树形结构展示设备类型层级 | P0 |
| 设备类型新增 | 创建新设备类型 | P0 |
| 设备类型编辑 | 修改设备类型信息 | P0 |
| 设备类型删除 | 删除设备类型（需确认无关联设备） | P0 |
| 设备类型编码 | 设备类型唯一编码 | P0 |
| 关联维护项 | 为设备类型关联可执行的维护项 | P0 |

### 2.2 设备厂商管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备厂商列表 | 展示所有设备厂商 | P0 |
| 厂商新增 | 创建新厂商 | P0 |
| 厂商编辑 | 修改厂商信息 | P0 |
| 厂商删除 | 删除厂商（需确认无关联型号） | P0 |
| 厂商编码 | 厂商唯一编码 | P0 |

### 2.3 设备型号管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 设备型号列表 | 展示所有设备型号 | P0 |
| 型号新增 | 创建新设备型号 | P0 |
| 型号编辑 | 修改型号信息 | P0 |
| 型号删除 | 删除型号（需确认无关联设备） | P0 |
| 关联设备类型 | 关联设备类型（通过类型编码） | P0 |
| 关联设备厂商 | 关联设备厂商（通过厂商编码） | P0 |

### 2.4 专题管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 专题列表 | 展示所有专题 | P0 |
| 专题新增 | 创建新专题 | P0 |
| 专题编辑 | 修改专题信息 | P0 |
| 专题删除 | 删除专题（需确认无关联数据） | P0 |
| 专题名称 | 专题显示名称 | P0 |
| 专题编码 | 专题唯一编码 | P0 |

### 2.5 专题设备类型配置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 专题设备类型列表 | 查看专题下可管理的设备类型 | P0 |
| 添加设备类型 | 为专题添加可管理的设备类型 | P0 |
| 移除设备类型 | 移除专题下的设备类型 | P0 |

### 2.6 用户专题权限配置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 用户专题列表 | 查看用户可访问的专题列表 | P0 |
| 添加用户专题 | 为用户添加可访问的专题 | P0 |
| 移除用户专题 | 移除用户的专题访问权限 | P0 |

### 2.7 维护公司管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 维护公司列表 | 展示所有维护公司 | P0 |
| 公司新增 | 创建新维护公司 | P0 |
| 公司编辑 | 修改公司信息 | P0 |
| 公司删除 | 删除公司（需确认无关联工单） | P0 |
| 可维护型号配置 | 配置公司可维护的设备型号 | P0 |

### 2.8 维护项配置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 维护项列表 | 展示所有维护项模板 | P0 |
| 维护项新增 | 创建新维护项 | P0 |
| 维护项编辑 | 修改维护项信息 | P0 |
| 维护项删除 | 删除维护项 | P0 |
| 维护周期设置 | 设置维护项的执行周期 | P0 |
| 维护项模板 | 维护项标准作业模板 | P0 |

### 2.9 巡检模板配置

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 巡检模板列表 | 展示所有巡检模板 | P0 |
| 巡检模板新增 | 创建新巡检模板 | P0 |
| 巡检模板编辑 | 修改巡检模板信息 | P0 |
| 巡检模板删除 | 删除巡检模板 | P0 |
| 巡检项配置 | 配置巡检模板包含的巡检项 | P0 |
| 巡检周期设置 | 设置巡检任务的执行周期 | P0 |

### 2.10 备件库管理

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 备件分类 | 备件分类管理 | P0 |
| 备件库存 | 备件库存查看和管理 | P0 |
| 备件新增 | 添加新备件 | P0 |
| 备件编辑 | 修改备件信息 | P0 |
| 备件删除 | 删除备件 | P0 |
| 备件申请 | 提交备件使用申请 | P0 |
| 备件审批 | 审批备件使用申请 | P0 |
| 库存预警 | 设置库存预警阈值 | P1 |

## 3. 数据模型

### 3.1 设备类型表 (cfg_device_type)

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
| project_id | BIGINT | 所属项目ID |
| parent_id | BIGINT | 父类型ID |
| code | VARCHAR(50) | 设备类型编码 |
| name | VARCHAR(100) | 设备类型名称 |
| sort | INT | 排序 |

### 3.2 设备厂商表 (cfg_manufacturer)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 厂商编码 |
| name | VARCHAR(100) | 厂商名称 |
| contact | VARCHAR(50) | 联系人 |
| phone | VARCHAR(20) | 联系电话 |
| address | VARCHAR(200) | 地址 |

### 3.3 设备型号表 (cfg_device_model)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 型号编码 |
| name | VARCHAR(100) | 型号名称 |
| device_type_code | VARCHAR(50) | 设备类型编码 |
| manufacturer_code | VARCHAR(50) | 厂商编码 |

### 3.4 专题表 (cfg_topic)

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
| code | VARCHAR(50) | 专题编码 |
| name | VARCHAR(100) | 专题名称 |
| description | VARCHAR(200) | 描述 |
| status | VARCHAR(20) | 状态 |

### 3.5 专题设备类型关联表 (cfg_topic_device_type)

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
| topic_id | BIGINT | 专题ID |
| device_type_id | BIGINT | 设备类型ID |

### 3.6 用户专题关联表 (cfg_user_topic)

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
| topic_id | BIGINT | 专题ID |

### 3.7 维护公司表 (cfg_maintenance_company)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 公司编码 |
| name | VARCHAR(100) | 公司名称 |
| contact | VARCHAR(50) | 联系人 |
| phone | VARCHAR(20) | 联系电话 |
| address | VARCHAR(200) | 地址 |

### 3.8 维护公司可维护型号关联表 (cfg_company_model)

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
| company_id | BIGINT | 维护公司ID |
| model_id | BIGINT | 设备型号ID |

### 3.9 维护项表 (cfg_maintenance_item)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 维护项编码 |
| name | VARCHAR(100) | 维护项名称 |
| description | TEXT | 描述 |
| cycle_type | VARCHAR(20) | 周期类型（DAILY-每日，WEEKLY-每周，MONTHLY-每月，QUARTERLY-每季度，YEARLY-每年） |
| cycle_days | INT | 周期天数 |
| template | TEXT | 维护作业模板 |

### 3.10 设备类型维护项关联表 (cfg_device_type_maintenance)

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
| device_type_id | BIGINT | 设备类型ID |
| maintenance_item_id | BIGINT | 维护项ID |

### 3.11 巡检模板表 (cfg_inspection_template)

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
| project_id | BIGINT | 所属项目ID |
| code | VARCHAR(50) | 模板编码 |
| name | VARCHAR(100) | 模板名称 |
| cycle_type | VARCHAR(20) | 周期类型 |
| cycle_days | INT | 周期天数 |
| description | TEXT | 描述 |

### 3.12 巡检项表 (cfg_inspection_item)

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
| template_id | BIGINT | 巡检模板ID |
| name | VARCHAR(100) | 巡检项名称 |
| description | TEXT | 巡检项描述 |
| inspection_type | VARCHAR(20) | 巡检类型（CHECKBOX-检查项，INPUT-输入项，SELECT-选择项） |
| options | TEXT | 选项（JSON格式，用于选择型巡检项） |
| required | SMALLINT | 是否必填（0-否，1-是） |
| sort | INT | 排序 |

### 3.13 备件分类表 (cfg_spare_part_category)

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
| project_id | BIGINT | 所属项目ID |
| parent_id | BIGINT | 父分类ID |
| name | VARCHAR(50) | 分类名称 |
| code | VARCHAR(50) | 分类编码 |
| sort | INT | 排序 |

### 3.14 备件表 (cfg_spare_part)

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
| project_id | BIGINT | 所属项目ID |
| category_id | BIGINT | 备件分类ID |
| code | VARCHAR(50) | 备件编码 |
| name | VARCHAR(100) | 备件名称 |
| specification | VARCHAR(100) | 规格型号 |
| unit | VARCHAR(20) | 单位 |
| stock_quantity | DECIMAL(10,2) | 当前库存数量 |
| warn_quantity | DECIMAL(10,2) | 预警库存数量 |
| price | DECIMAL(10,2) | 单价 |

### 3.15 备件申请记录表 (cfg_spare_part_record)

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
| project_id | BIGINT | 所属项目ID |
| spare_part_id | BIGINT | 备件ID |
| quantity | DECIMAL(10,2) | 申请数量 |
| status | VARCHAR(20) | 状态（PENDING-待审批，APPROVED-已批准，REJECTED-已拒绝，COMPLETED-已完成） |
| applicant_id | BIGINT | 申请人ID |
| approver_id | BIGINT | 审批人ID |
| approve_time | TIMESTAMP | 审批时间 |
| approve_remark | VARCHAR(200) | 审批备注 |

## 4. API接口

### 4.1 设备类型接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/device-types | 设备类型树列表 |
| POST | /api/config/device-types | 创建设备类型 |
| PUT | /api/config/device-types/{id} | 更新设备类型 |
| DELETE | /api/config/device-types/{id} | 删除设备类型 |
| GET | /api/config/device-types/{id}/maintenance-items | 获取类型关联的维护项 |
| POST | /api/config/device-types/{id}/maintenance-items | 关联维护项 |

### 4.2 设备厂商接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/manufacturers | 厂商列表 |
| POST | /api/config/manufacturers | 创建厂商 |
| PUT | /api/config/manufacturers/{id} | 更新厂商 |
| DELETE | /api/config/manufacturers/{id} | 删除厂商 |

### 4.3 设备型号接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/device-models | 型号列表 |
| POST | /api/config/device-models | 创建型号 |
| PUT | /api/config/device-models/{id} | 更新型号 |
| DELETE | /api/config/device-models/{id} | 删除型号 |

### 4.4 专题接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/topics | 专题列表 |
| POST | /api/config/topics | 创建专题 |
| PUT | /api/config/topics/{id} | 更新专题 |
| DELETE | /api/config/topics/{id} | 删除专题 |
| GET | /api/config/topics/{id}/device-types | 获取专题设备类型 |
| POST | /api/config/topics/{id}/device-types | 添加专题设备类型 |
| DELETE | /api/config/topics/{id}/device-types/{typeId} | 移除专题设备类型 |

### 4.5 用户专题接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/users/{userId}/topics | 获取用户专题 |
| POST | /api/config/users/{userId}/topics | 添加用户专题 |
| DELETE | /api/config/users/{userId}/topics/{topicId} | 移除用户专题 |

### 4.6 维护公司接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/maintenance-companies | 维护公司列表 |
| POST | /api/config/maintenance-companies | 创建维护公司 |
| PUT | /api/config/maintenance-companies/{id} | 更新维护公司 |
| DELETE | /api/config/maintenance-companies/{id} | 删除维护公司 |
| GET | /api/config/maintenance-companies/{id}/models | 获取公司可维护型号 |
| POST | /api/config/maintenance-companies/{id}/models | 配置可维护型号 |
| DELETE | /api/config/maintenance-companies/{id}/models/{modelId} | 移除可维护型号 |

### 4.7 维护项接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/maintenance-items | 维护项列表 |
| POST | /api/config/maintenance-items | 创建维护项 |
| PUT | /api/config/maintenance-items/{id} | 更新维护项 |
| DELETE | /api/config/maintenance-items/{id} | 删除维护项 |

### 4.8 巡检模板接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/inspection-templates | 巡检模板列表 |
| POST | /api/config/inspection-templates | 创建巡检模板 |
| PUT | /api/config/inspection-templates/{id} | 更新巡检模板 |
| DELETE | /api/config/inspection-templates/{id} | 删除巡检模板 |
| GET | /api/config/inspection-templates/{id}/items | 获取模板巡检项 |
| POST | /api/config/inspection-templates/{id}/items | 添加巡检项 |
| PUT | /api/config/inspection-templates/{id}/items/{itemId} | 更新巡检项 |
| DELETE | /api/config/inspection-templates/{id}/items/{itemId} | 删除巡检项 |

### 4.9 备件库接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/config/spare-part-categories | 备件分类列表 |
| POST | /api/config/spare-part-categories | 创建备件分类 |
| PUT | /api/config/spare-part-categories/{id} | 更新备件分类 |
| DELETE | /api/config/spare-part-categories/{id} | 删除备件分类 |
| GET | /api/config/spare-parts | 备件列表 |
| POST | /api/config/spare-parts | 创建备件 |
| PUT | /api/config/spare-parts/{id} | 更新备件 |
| DELETE | /api/config/spare-parts/{id} | 删除备件 |
| GET | /api/config/spare-parts/{id}/records | 备件使用记录 |
| POST | /api/config/spare-part-records | 申请备件 |
| PUT | /api/config/spare-part-records/{id}/approve | 审批备件申请 |
| PUT | /api/config/spare-part-records/{id}/complete | 确认备件使用 |

## 5. 前端页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 设备类型管理 | /config/device-types | 设备类型树管理 |
| 设备厂商管理 | /config/manufacturers | 厂商列表 |
| 设备型号管理 | /config/device-models | 型号列表 |
| 专题管理 | /config/topics | 专题列表 |
| 专题设备类型 | /config/topics/:id/device-types | 专题设备类型配置 |
| 用户专题权限 | /config/user-topics | 用户专题权限配置 |
| 维护公司管理 | /config/maintenance-companies | 维护公司列表 |
| 维护项配置 | /config/maintenance-items | 维护项列表 |
| 巡检模板配置 | /config/inspection-templates | 巡检模板列表 |
| 备件库管理 | /config/spare-parts | 备件库管理 |

## 6. 业务流程

### 6.1 设备类型配置流程

```
1. 进入设备类型管理页面
2. 创建设备类型树（支持多级层级）
3. 为设备类型设置唯一编码
4. 为设备类型关联可执行的维护项
5. 关联后，设备类型下的设备可执行对应维护项
```

### 6.2 专题设备类型配置流程

```
1. 创建专题（设置专题编码和名称）
2. 进入专题详情
3. 配置该专题可管理的设备类型
4. 配置后，该专题下的设备只能是已关联的设备类型
```

### 6.3 用户专题权限配置流程

```
1. 进入用户专题权限配置页面
2. 选择目标用户
3. 为用户分配可访问的专题
4. 用户登录后只能看到分配给他的专题数据
```

### 6.4 维护公司型号配置流程

```
1. 创建维护公司
2. 进入维护公司详情
3. 配置该公司可维护的设备型号
4. 创建工单时，只能分配给有对应型号维护能力的公司
```

## 7. 验证标准

### 7.1 单元测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 设备类型CRUD | 类型增删改查，树形结构 | 功能正常 | P0 |
| 设备厂商CRUD | 厂商增删改查 | 功能正常 | P0 |
| 设备型号CRUD | 型号增删改查，关联验证 | 功能正常 | P0 |
| 专题CRUD | 专题增删改查 | 功能正常 | P0 |
| 专题设备类型关联 | 添加、移除设备类型 | 关联正确 | P0 |
| 用户专题关联 | 添加、移除用户专题 | 关联正确 | P0 |
| 维护项配置 | 维护项增删改查，周期设置 | 功能正常 | P0 |
| 巡检模板配置 | 模板增删改查，巡检项配置 | 功能正常 | P0 |
| 备件库管理 | 备件增删改查，库存管理 | 功能正常 | P0 |
| 项目数据隔离 | 不同项目配置数据隔离 | 隔离正确 | P0 |

### 7.2 集成测试验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 项目切换 | 切换项目后配置数据更新 | 配置数据随项目切换 | P0 |
| 关联数据验证 | 删除被引用的配置项 | 提示不能删除或级联删除 | P0 |
| 缓存一致性 | 配置更新后缓存刷新 | 缓存正确更新 | P0 |

### 7.3 UI交互验证标准

| 测试项 | 验证要点 | 预期结果 | 优先级 |
|--------|----------|----------|--------|
| 设备类型树 | 树形结构展示和操作 | 操作流畅，层级正确 | P0 |
| 关联选择 | 多选设备类型/型号 | 选择正确保存 | P0 |
| 周期配置 | 周期类型和天数配置 | 配置正确 | P0 |

### 7.4 验收测试用例

#### 测试用例 01: 设备类型管理

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 进入设备类型管理页面 | 显示设备类型树 |
| 2 | 点击"新增类型"按钮 | 打开新增表单 |
| 3 | 填写类型名称和编码，选择父类型 | 保存成功 |
| 4 | 为设备类型关联维护项 | 关联成功 |
| 5 | 查看设备类型列表 | 显示树形结构 |

#### 测试用例 02: 专题配置

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 创建专题"排水管网监测" | 专题创建成功 |
| 2 | 进入专题详情 | 显示专题信息 |
| 3 | 添加可管理的设备类型 | 添加成功 |
| 4 | 为用户分配该专题 | 用户可访问该专题数据 |

#### 测试用例 03: 巡检模板配置

| 步骤 | 操作 | 预期结果 |
|------|------|----------|
| 1 | 创建巡检模板"日常巡检" | 模板创建成功 |
| 2 | 配置巡检周期为每周一次 | 周期设置成功 |
| 3 | 添加巡检项（检查设备状态、记录数值） | 巡检项添加成功 |
| 4 | 设置巡检项类型为输入型 | 类型设置正确 |
