# AI Agent 入口文档

## 概述

本文档是多项目设备安装运维平台开发任务的AI Agent主入口，记录仓库结构、关键文件索引、硬约束、文件读取顺序等信息。所有Agent在进行开发任务前必须阅读本文档。

---

## 一、仓库结构

```
/workspace/
└── .trae/
    └── specs/
        └── multi-project-device-platform/
            ├── features/                    # 功能模块详细规格文档
            │   ├── 01-project-management.md      # 项目管理模块
            │   ├── 02-device-management.md        # 设备管理模块
            │   ├── 03-installation-management.md  # 安装管理模块
            │   ├── 04-operation-management.md     # 运维管理模块
            │   ├── 05-report-statistics.md         # 报表统计模块
            │   ├── 06-system-management.md        # 系统管理模块
            │   └── 07-business-config.md          # 业务配置模块
            ├── spec.md                      # 主规格说明书（技术要求）
            ├── DATABASE-DESIGN.md           # 数据库设计规范
            ├── CODE-STANDARDS.md            # 代码编码规范
            ├── tasks.md                    # 任务清单
            └── checklist.md                # 检查清单
```

---

## 二、关键文件索引

### 2.1 规格文档（必读）

| 优先级 | 文件 | 说明 |
|--------|------|------|
| P0 | [spec.md](./spec.md) | 主规格说明书，包含技术架构、技术栈、系统架构图 |
| P0 | [features/06-system-management.md](./features/06-system-management.md) | 系统管理模块详细规格 |
| P0 | [features/01-project-management.md](./features/01-project-management.md) | 项目管理模块详细规格 |
| P0 | [features/02-device-management.md](./features/02-device-management.md) | 设备管理模块详细规格 |
| P0 | [features/07-business-config.md](./features/07-business-config.md) | 业务配置模块详细规格 |
| P1 | [DATABASE-DESIGN.md](./DATABASE-DESIGN.md) | 数据库设计规范 |
| P1 | [CODE-STANDARDS.md](./CODE-STANDARDS.md) | 代码编码规范（Java + Vue） |
| P2 | [features/03-05/*.md](./features/) | 其他模块规格文档 |

### 2.2 技术栈汇总

| 层级 | 技术 | 版本要求 |
|------|------|----------|
| 后端语言 | Java | 17 |
| 后端框架 | Spring Boot | 3.5.x |
| 数据库 | PostgreSQL | 13.x+ |
| 缓存 | Redis | 6.x+ |
| 对象存储 | MinIO | 最新稳定版 |
| ORM | MyBatis-Plus | 最新稳定版 |
| ID生成 | 雪花算法 | - |
| 认证 | Sa-Token | 最新稳定版 |
| 前端框架 | Vue 3 | Composition API |
| 构建工具 | Vite | 最新稳定版 |
| PC端UI | Element Plus | 最新稳定版 |
| 移动端UI | Vant | 最新稳定版 |
| 状态管理 | Pinia | 最新稳定版 |

### 2.3 四大核心模块

1. **系统管理模块** - 认证、用户、角色、权限、配置、日志
2. **项目管理模块** - 项目、成员、文档、阶段
3. **业务配置模块** - 设备类型、厂商、型号、专题、维护公司、维护项、巡检模板、备件库
4. **设备管理模块** - 设备台账、设备档案、巡检、维护工单、故障管理、统计

---

## 三、硬约束

### 3.1 开发前必须

- [ ] 完整阅读 [spec.md](./spec.md)
- [ ] 完整阅读目标模块的 features/*.md 文档
- [ ] 完整阅读 [DATABASE-DESIGN.md](./DATABASE-DESIGN.md)
- [ ] 完整阅读 [CODE-STANDARDS.md](./CODE-STANDARDS.md)

### 3.2 技术约束

| 约束项 | 要求 |
|--------|------|
| Java版本 | 必须是Java 17，不允许使用其他版本 |
| Spring Boot版本 | 必须是3.5.x，不允许降级 |
| 数据库 | PostgreSQL，禁止使用SQLite/MySQL |
| 主键生成 | 必须使用雪花算法，禁止使用自增ID |
| 认证框架 | 必须使用Sa-Token |
| 通用字段 | 所有业务表必须包含id、create_by、create_dept_id、update_by、create_time、update_time、deleted、remark |
| 缓存 | 必须使用Redis，不允许使用本地缓存 |
| 文件存储 | 必须使用MinIO，不允许存储到数据库或本地磁盘 |
| 前端响应式 | PC端(≥1024px)使用Element Plus，移动端(<768px)使用Vant |
| 二维码 | 设备不包含二维码功能 |

### 3.3 代码规范约束

| 约束项 | 要求 |
|--------|------|
| 缩进 | 4个空格，禁止使用Tab |
| 括号风格 | 左大括号不换行 |
| 命名规范 | 遵循 [CODE-STANDARDS.md](./CODE-STANDARDS.md) 的命名规范 |
| 注释 | 类和public方法必须添加JavaDoc |
| 返回格式 | 统一使用`Result<T>`封装 |
| 异常处理 | 使用自定义业务异常，不直接吞掉异常 |
| 敏感信息 | 禁止记录密码、密钥等到日志 |

### 3.4 数据库约束

| 约束项 | 要求 |
|--------|------|
| 表名前缀 | sys_（系统）、prj_（项目）、cfg_（配置）、dev_（设备） |
| 索引命名 | idx_{表名}_{字段名} |
| 外键约束 | 根据业务需求适当使用，禁止过度使用外键 |
| 软删除 | 使用deleted字段，不使用物理删除 |
| 字段类型 | 遵循PostgreSQL最佳实践 |

### 3.5 安全约束

- [ ] 密码必须使用BCrypt加密存储
- [ ] SQL注入防护（使用参数化查询）
- [ ] XSS攻击防护
- [ ] CORS跨域配置
- [ ] 接口限流（基于Redis）
- [ ] 文件上传安全验证（类型、大小、内容检查）
- [ ] MinIO访问权限控制

### 3.6 禁止事项

- [x] 禁止在用户明确要求开始开发前自动开始编写代码
- [x] 禁止创建不必要的文档文件（*.md）
- [x] 禁止引入未在规格文档中指定的依赖
- [x] 禁止硬编码敏感信息（密钥、密码等）
- [x] 禁止使用不安全的加密算法

---

## 四、文件读取顺序

### 4.1 开发新模块时的阅读顺序

```
1. spec.md                          ← 技术架构概述、技术栈、模块划分
   ↓
2. features/{module}.md            ← 目标模块的详细功能规格
   ↓
3. DATABASE-DESIGN.md              ← 数据库表设计、字段规范
   ↓
4. CODE-STANDARDS.md               ← 编码规范、命名规则
   ↓
5. tasks.md / checklist.md         ← 任务分解和检查清单
```

### 4.2 开发系统管理模块时的阅读顺序

```
1. spec.md (§7.1 系统管理模块)
   ↓
2. features/06-system-management.md
   ↓
3. DATABASE-DESIGN.md (§sys_* 表设计)
   ↓
4. CODE-STANDARDS.md (§Java编码规范)
```

### 4.3 开发设备管理模块时的阅读顺序

```
1. spec.md (§7.4 设备管理模块)
   ↓
2. features/02-device-management.md
   ↓
3. features/07-business-config.md ← 设备类型、型号等配置
   ↓
4. DATABASE-DESIGN.md (§dev_* 表设计)
   ↓
5. CODE-STANDARDS.md
```

### 4.4 修复Bug时的阅读顺序

```
1. 相关模块的 features/{module}.md ← 了解预期行为
   ↓
2. 相关模块的 entity/             ← 检查数据模型定义
   ↓
3. 相关模块的 service/            ← 检查业务逻辑
   ↓
4. 相关模块的 controller/          ← 检查接口定义
```

---

## 五、开发工作流程

### 5.1 任务开始前

1. 阅读本文档确认已理解仓库结构和硬约束
2. 按4.x节的顺序阅读相关规格文档
3. 使用 TodoWrite 工具创建任务清单
4. 向用户确认开发范围和优先级

### 5.2 开发过程中

1. 遵循 [CODE-STANDARDS.md](./CODE-STANDARDS.md) 的编码规范
2. 遵循 [DATABASE-DESIGN.md](./DATABASE-DESIGN.md) 的数据库规范
3. 完成后运行 lint/typecheck 命令验证代码质量
4. 定期向用户汇报进度

### 5.3 任务完成后

1. 自行验证实现是否符合规格文档
2. 运行测试确保功能正常
3. 更新 tasks.md 标记完成的任务
4. 清理不必要的临时文件

---

## 六、关键业务规则

### 6.1 项目数据隔离

- 所有业务配置数据按项目隔离
- 用户通过专题权限控制可访问的数据范围
- 项目之间数据完全隔离
- 设备必须属于某个项目和某个专题

### 6.2 Redis缓存使用场景

| 场景 | 说明 |
|------|------|
| 会话管理 | 存储用户登录Token和会话信息 |
| 字典数据缓存 | 缓存系统字典数据 |
| 首页仪表盘数据 | 缓存统计数据 |
| 热点数据缓存 | 缓存频繁访问的设备/项目信息 |
| 分布式锁 | 防止重复提交、资源竞争 |
| 接口限流 | 基于Redis实现访问频率限制 |

### 6.3 MinIO对象存储使用场景

| 场景 | 说明 |
|------|------|
| 设备照片 | 设备照片、现场照片 |
| 设备档案附件 | 技术文档、维保记录等 |
| 项目文档 | 项目相关文档 |
| 巡检记录图片 | 巡检过程中拍摄的图片 |
| 故障现场照片 | 设备故障现场照片 |
| 用户头像 | 用户头像图片 |

### 6.4 通用字段规范

所有业务表必须包含以下字段：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法生成） |
| create_by | BIGINT | 创建人ID |
| create_dept_id | BIGINT | 创建人部门ID |
| update_by | BIGINT | 更新人ID |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| deleted | SMALLINT | 是否删除（0-未删除，1-已删除） |
| remark | VARCHAR(500) | 备注 |

---

## 七、验证标准

每个模块开发完成后，必须按以下标准进行验证：

1. **功能完整性** - 实现规格文档中的所有功能点
2. **接口正确性** - API响应格式符合 `Result<T>` 规范
3. **数据正确性** - 数据库操作符合设计规范
4. **代码质量** - 通过 lint/typecheck 检查
5. **安全性** - 无SQL注入、XSS等安全问题

---

## 八、常用命令

### 8.1 后端命令

```bash
# 构建项目
mvn clean package -DskipTests

# 运行项目
mvn spring-boot:run

# 运行测试
mvn test

# 代码检查
mvn checkstyle:check
```

### 8.2 前端命令

```bash
# 安装依赖
npm install

# 开发模式
npm run dev

# 构建生产版本
npm run build

# Lint检查
npm run lint
```

---

## 九、联系与反馈

如在开发过程中遇到规格文档未覆盖的问题，请：

1. 先尝试按现有规格和编码规范解决
2. 如无法解决，向用户报告并请求澄清
3. 不要自行决定添加规格外的新功能

---

**本文档最后更新：2026-05-14**
