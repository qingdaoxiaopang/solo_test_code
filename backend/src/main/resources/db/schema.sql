-- ================================================
-- 多项目设备安装运维平台 - 数据库表结构
-- 数据库: PostgreSQL
-- 版本: 1.0.0
-- ================================================

-- 创建表空间和数据库（可选，根据实际情况调整）
-- CREATE TABLESPACE operation_space LOCATION '/data/postgresql/operation';
-- CREATE DATABASE operation_db TABLESPACE = operation_space;

-- 开启扩展
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ================================================
-- 第一部分：系统管理表
-- ================================================

-- 用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nick_name VARCHAR(100) COMMENT '昵称',
    real_name VARCHAR(100) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(500) COMMENT '头像URL',
    sex VARCHAR(10) DEFAULT 'unknown' COMMENT '性别: male/female/unknown',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    login_ip VARCHAR(50) COMMENT '最后登录IP',
    login_date TIMESTAMP COMMENT '最后登录时间',
    dept_id BIGINT COMMENT '部门ID',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志: 0未删除/1已删除',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_sys_user_username ON sys_user(username);
CREATE INDEX idx_sys_user_dept_id ON sys_user(dept_id);
CREATE INDEX idx_sys_user_status ON sys_user(status);

-- 角色表
CREATE TABLE sys_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) NOT NULL COMMENT '角色标识',
    role_sort INT DEFAULT 0 COMMENT '显示顺序',
    data_scope VARCHAR(50) DEFAULT '1' COMMENT '数据范围',
    menu_check_strictly BOOLEAN DEFAULT TRUE COMMENT '菜单树选择框是否级联',
    dept_check_strictly BOOLEAN DEFAULT TRUE COMMENT '部门树选择框是否级联',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_sys_role_key ON sys_role(role_key);
CREATE INDEX idx_sys_role_status ON sys_role(status);

-- 权限表
CREATE TABLE sys_permission (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    code VARCHAR(100) NOT NULL COMMENT '权限标识',
    type VARCHAR(10) DEFAULT 'menu' COMMENT '类型: menu/button',
    icon VARCHAR(100) COMMENT '图标',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    visible VARCHAR(10) DEFAULT '1' COMMENT '状态: 1显示/0隐藏',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    keep_alive BOOLEAN DEFAULT TRUE COMMENT '是否缓存',
    always_show BOOLEAN DEFAULT TRUE COMMENT '是否总是显示',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_sys_permission_code ON sys_permission(code);
CREATE INDEX idx_sys_permission_parent_id ON sys_permission(parent_id);
CREATE INDEX idx_sys_permission_type ON sys_permission(type);

-- 用户角色关联表
CREATE TABLE sys_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_sys_user_role_user_id ON sys_user_role(user_id);
CREATE INDEX idx_sys_user_role_role_id ON sys_user_role(role_id);
CREATE UNIQUE INDEX idx_sys_user_role_unique ON sys_user_role(user_id, role_id);

-- 角色权限关联表
CREATE TABLE sys_role_permission (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_sys_role_permission_role_id ON sys_role_permission(role_id);
CREATE INDEX idx_sys_role_permission_permission_id ON sys_role_permission(permission_id);
CREATE UNIQUE INDEX idx_sys_role_permission_unique ON sys_role_permission(role_id, permission_id);

-- 部门表
CREATE TABLE sys_dept (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    ancestors VARCHAR(500) DEFAULT '' COMMENT '祖级列表',
    dept_name VARCHAR(100) NOT NULL COMMENT '部门名称',
    dept_code VARCHAR(100) COMMENT '部门编码',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_sys_dept_parent_id ON sys_dept(parent_id);
CREATE INDEX idx_sys_dept_status ON sys_dept(status);

-- 字典类型表
CREATE TABLE sys_dict (
    id BIGSERIAL PRIMARY KEY,
    dict_name VARCHAR(100) NOT NULL COMMENT '字典名称',
    dict_code VARCHAR(100) NOT NULL COMMENT '字典编码',
    dict_type VARCHAR(100) DEFAULT 'default' COMMENT '字典类型',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_sys_dict_code ON sys_dict(dict_code);

-- 字典数据表
CREATE TABLE sys_dict_item (
    id BIGSERIAL PRIMARY KEY,
    dict_id BIGINT NOT NULL COMMENT '字典ID',
    dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
    dict_value VARCHAR(100) NOT NULL COMMENT '字典键值',
    dict_type VARCHAR(100) DEFAULT 'string' COMMENT '数据类型',
    css_class VARCHAR(100) COMMENT '样式属性',
    list_class VARCHAR(100) COMMENT '回显样式',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    is_default VARCHAR(10) DEFAULT '0' COMMENT '是否默认: 1是/0否',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_sys_dict_item_dict_id ON sys_dict_item(dict_id);
CREATE INDEX idx_sys_dict_item_dict_value ON sys_dict_item(dict_value);

-- 操作日志表
CREATE TABLE sys_log (
    id BIGSERIAL PRIMARY KEY,
    module VARCHAR(100) COMMENT '操作模块',
    type VARCHAR(20) DEFAULT 'info' COMMENT '操作类型: info/warning/error',
    method VARCHAR(200) COMMENT '请求方法',
    request_method VARCHAR(10) COMMENT '请求方式',
    operator_type VARCHAR(20) COMMENT '操作类型: 1后端/2移动端',
    user_id BIGINT COMMENT '用户ID',
    username VARCHAR(50) COMMENT '操作人',
    url VARCHAR(500) COMMENT '请求URL',
    ip VARCHAR(50) COMMENT 'IP地址',
    location VARCHAR(255) COMMENT '操作地点',
    params TEXT COMMENT '请求参数',
    result TEXT COMMENT '返回结果',
    status VARCHAR(10) DEFAULT '1' COMMENT '操作状态: 1成功/0失败',
    error_msg TEXT COMMENT '错误信息',
    operate_time BIGINT COMMENT '操作时长(毫秒)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
);

CREATE INDEX idx_sys_log_user_id ON sys_log(user_id);
CREATE INDEX idx_sys_log_type ON sys_log(type);
CREATE INDEX idx_sys_log_status ON sys_log(status);
CREATE INDEX idx_sys_log_create_time ON sys_log(create_time);

-- API密钥表
CREATE TABLE sys_api_key (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    api_key VARCHAR(64) NOT NULL UNIQUE COMMENT 'API密钥',
    secret_key VARCHAR(64) NOT NULL COMMENT '密钥',
    name VARCHAR(100) NOT NULL COMMENT '密钥名称',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    expire_time TIMESTAMP COMMENT '过期时间',
    last_used_time TIMESTAMP COMMENT '最后使用时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_sys_api_key_user_id ON sys_api_key(user_id);
CREATE UNIQUE INDEX idx_sys_api_key_api_key ON sys_api_key(api_key);

-- ================================================
-- 第二部分：项目管理表
-- ================================================

-- 项目表
CREATE TABLE prj_project (
    id BIGSERIAL PRIMARY KEY,
    project_code VARCHAR(50) NOT NULL UNIQUE COMMENT '项目编码',
    project_name VARCHAR(200) NOT NULL COMMENT '项目名称',
    project_type VARCHAR(50) COMMENT '项目类型',
    description TEXT COMMENT '项目描述',
    cover_image VARCHAR(500) COMMENT '封面图片',
    budget DECIMAL(15,2) COMMENT '预算金额',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    status VARCHAR(20) DEFAULT 'draft' COMMENT '状态: draft/planning/in_progress/on_hold/completed/cancelled',
    progress INT DEFAULT 0 COMMENT '进度百分比',
    manager_id BIGINT COMMENT '项目经理ID',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_prj_project_code ON prj_project(project_code);
CREATE INDEX idx_prj_project_status ON prj_project(status);
CREATE INDEX idx_prj_project_manager_id ON prj_project(manager_id);

-- 项目成员表
CREATE TABLE prj_project_member (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role VARCHAR(50) DEFAULT 'member' COMMENT '项目角色: manager/developer/tester/member',
    join_date DATE COMMENT '加入日期',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1参与/0离开',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_prj_project_member_project_id ON prj_project_member(project_id);
CREATE INDEX idx_prj_project_member_user_id ON prj_project_member(user_id);
CREATE UNIQUE INDEX idx_prj_project_member_unique ON prj_project_member(project_id, user_id);

-- 项目阶段表
CREATE TABLE prj_project_stage (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    stage_name VARCHAR(200) NOT NULL COMMENT '阶段名称',
    stage_code VARCHAR(50) COMMENT '阶段编码',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending/in_progress/completed',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_prj_project_stage_project_id ON prj_project_stage(project_id);
CREATE INDEX idx_prj_project_stage_status ON prj_project_stage(status);

-- 项目文档表
CREATE TABLE prj_project_document (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    stage_id BIGINT COMMENT '阶段ID',
    title VARCHAR(200) NOT NULL COMMENT '文档标题',
    doc_type VARCHAR(50) COMMENT '文档类型',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小',
    file_type VARCHAR(50) COMMENT '文件类型',
    version VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_prj_project_document_project_id ON prj_project_document(project_id);
CREATE INDEX idx_prj_project_document_stage_id ON prj_project_document(stage_id);

-- ================================================
-- 第三部分：业务配置表
-- ================================================

-- 设备类型表
CREATE TABLE cfg_device_type (
    id BIGSERIAL PRIMARY KEY,
    type_code VARCHAR(50) NOT NULL UNIQUE COMMENT '类型编码',
    type_name VARCHAR(100) NOT NULL COMMENT '类型名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父类型ID',
    icon VARCHAR(100) COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_cfg_device_type_code ON cfg_device_type(type_code);
CREATE INDEX idx_cfg_device_type_parent_id ON cfg_device_type(parent_id);

-- 厂商表
CREATE TABLE cfg_manufacturer (
    id BIGSERIAL PRIMARY KEY,
    manufacturer_code VARCHAR(50) NOT NULL UNIQUE COMMENT '厂商编码',
    manufacturer_name VARCHAR(200) NOT NULL COMMENT '厂商名称',
    contact_person VARCHAR(50) COMMENT '联系人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(500) COMMENT '地址',
    logo VARCHAR(500) COMMENT 'Logo地址',
    website VARCHAR(200) COMMENT '官网',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_cfg_manufacturer_code ON cfg_manufacturer(manufacturer_code);

-- 设备型号表
CREATE TABLE cfg_device_model (
    id BIGSERIAL PRIMARY KEY,
    model_code VARCHAR(50) NOT NULL UNIQUE COMMENT '型号编码',
    model_name VARCHAR(200) NOT NULL COMMENT '型号名称',
    manufacturer_id BIGINT NOT NULL COMMENT '厂商ID',
    device_type_id BIGINT COMMENT '设备类型ID',
    specs TEXT COMMENT '规格参数(JSON)',
    image_urls TEXT COMMENT '图片URLs(JSON)',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_cfg_device_model_code ON cfg_device_model(model_code);
CREATE INDEX idx_cfg_device_model_manufacturer_id ON cfg_device_model(manufacturer_id);
CREATE INDEX idx_cfg_device_model_device_type_id ON cfg_device_model(device_type_id);

-- 话题表
CREATE TABLE cfg_topic (
    id BIGSERIAL PRIMARY KEY,
    topic_code VARCHAR(100) NOT NULL UNIQUE COMMENT '话题编码',
    topic_name VARCHAR(200) NOT NULL COMMENT '话题名称',
    topic_type VARCHAR(50) COMMENT '话题类型',
    qos VARCHAR(20) DEFAULT '1' COMMENT 'QoS级别: 0/1/2',
    description TEXT COMMENT '话题描述',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_cfg_topic_code ON cfg_topic(topic_code);
CREATE INDEX idx_cfg_topic_type ON cfg_topic(topic_type);

-- 话题与设备类型关联表
CREATE TABLE cfg_topic_device_type (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL COMMENT '话题ID',
    device_type_id BIGINT NOT NULL COMMENT '设备类型ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_cfg_topic_device_type_topic_id ON cfg_topic_device_type(topic_id);
CREATE INDEX idx_cfg_topic_device_type_device_type_id ON cfg_topic_device_type(device_type_id);
CREATE UNIQUE INDEX idx_cfg_topic_device_type_unique ON cfg_topic_device_type(topic_id, device_type_id);

-- 用户话题订阅表
CREATE TABLE cfg_user_topic (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    topic_id BIGINT NOT NULL COMMENT '话题ID',
    subscribe_status VARCHAR(20) DEFAULT 'active' COMMENT '订阅状态: active/paused/cancelled',
    notify_enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用通知',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE INDEX idx_cfg_user_topic_user_id ON cfg_user_topic(user_id);
CREATE INDEX idx_cfg_user_topic_topic_id ON cfg_user_topic(topic_id);
CREATE UNIQUE INDEX idx_cfg_user_topic_unique ON cfg_user_topic(user_id, topic_id);

-- 维保单位表
CREATE TABLE cfg_maintenance_company (
    id BIGSERIAL PRIMARY KEY,
    company_code VARCHAR(50) NOT NULL UNIQUE COMMENT '单位编码',
    company_name VARCHAR(200) NOT NULL COMMENT '单位名称',
    company_type VARCHAR(50) COMMENT '单位类型',
    contact_person VARCHAR(50) COMMENT '联系人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(500) COMMENT '地址',
    service_scope TEXT COMMENT '服务范围',
    qualification_level VARCHAR(50) COMMENT '资质等级',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_cfg_maintenance_company_code ON cfg_maintenance_company(company_code);

-- 厂商型号关联表
CREATE TABLE cfg_company_model (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL COMMENT '厂商ID',
    model_id BIGINT NOT NULL COMMENT '型号ID',
    certification_date DATE COMMENT '认证日期',
    certification_no VARCHAR(100) COMMENT '认证编号',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_cfg_company_model_company_id ON cfg_company_model(company_id);
CREATE INDEX idx_cfg_company_model_model_id ON cfg_company_model(model_id);
CREATE UNIQUE INDEX idx_cfg_company_model_unique ON cfg_company_model(company_id, model_id);

-- 备件分类表
CREATE TABLE cfg_spare_part_category (
    id BIGSERIAL PRIMARY KEY,
    category_code VARCHAR(50) NOT NULL UNIQUE COMMENT '分类编码',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_cfg_spare_part_category_code ON cfg_spare_part_category(category_code);
CREATE INDEX idx_cfg_spare_part_category_parent_id ON cfg_spare_part_category(parent_id);

-- 备件表
CREATE TABLE cfg_spare_part (
    id BIGSERIAL PRIMARY KEY,
    part_code VARCHAR(50) NOT NULL UNIQUE COMMENT '备件编码',
    part_name VARCHAR(200) NOT NULL COMMENT '备件名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    model_id BIGINT COMMENT '适用型号ID',
    manufacturer_id BIGINT COMMENT '生产厂商ID',
    unit VARCHAR(20) DEFAULT '个' COMMENT '单位',
    unit_price DECIMAL(10,2) COMMENT '单价',
    min_stock INT DEFAULT 0 COMMENT '最小库存',
    current_stock INT DEFAULT 0 COMMENT '当前库存',
    max_stock INT COMMENT '最大库存',
    image_url VARCHAR(500) COMMENT '图片URL',
    specs TEXT COMMENT '规格参数(JSON)',
    status VARCHAR(10) DEFAULT '1' COMMENT '状态: 1正常/0禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_cfg_spare_part_code ON cfg_spare_part(part_code);
CREATE INDEX idx_cfg_spare_part_category_id ON cfg_spare_part(category_id);
CREATE INDEX idx_cfg_spare_part_model_id ON cfg_cfg_spare_part(model_id);

-- 备件记录表
CREATE TABLE cfg_spare_part_record (
    id BIGSERIAL PRIMARY KEY,
    part_id BIGINT NOT NULL COMMENT '备件ID',
    record_type VARCHAR(20) NOT NULL COMMENT '记录类型: in/out/adjust/lock',
    quantity INT NOT NULL COMMENT '数量',
    before_stock INT COMMENT '变动前库存',
    after_stock INT COMMENT '变动后库存',
    order_id BIGINT COMMENT '关联工单ID',
    device_id BIGINT COMMENT '关联设备ID',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    reason VARCHAR(500) COMMENT '变动原因',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_cfg_spare_part_record_part_id ON cfg_spare_part_record(part_id);
CREATE INDEX idx_cfg_spare_part_record_type ON cfg_spare_part_record(record_type);
CREATE INDEX idx_cfg_spare_part_record_order_id ON cfg_spare_part_record(order_id);
CREATE INDEX idx_cfg_spare_part_record_create_time ON cfg_spare_part_record(create_time);

-- ================================================
-- 第四部分：设备管理表
-- ================================================

-- 设备表
CREATE TABLE dev_device (
    id BIGSERIAL PRIMARY KEY,
    device_code VARCHAR(100) NOT NULL UNIQUE COMMENT '设备编码',
    device_name VARCHAR(200) NOT NULL COMMENT '设备名称',
    device_type_id BIGINT NOT NULL COMMENT '设备类型ID',
    model_id BIGINT COMMENT '设备型号ID',
    manufacturer_id BIGINT COMMENT '生产厂商ID',
    serial_number VARCHAR(100) COMMENT '序列号',
    mac_address VARCHAR(100) COMMENT 'MAC地址',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    imei VARCHAR(100) COMMENT 'IMEI',
    project_id BIGINT COMMENT '所属项目ID',
    installation_site VARCHAR(500) COMMENT '安装位置',
    longitude DECIMAL(10,7) COMMENT '经度',
    latitude DECIMAL(10,7) COMMENT '纬度',
    install_date DATE COMMENT '安装日期',
    commission_date DATE COMMENT '投运日期',
    warranty_expire_date DATE COMMENT '保修到期日期',
    maintenance_company_id BIGINT COMMENT '维保单位ID',
    status VARCHAR(20) DEFAULT 'offline' COMMENT '状态: offline/online/running/fault/maintenance',
    running_status VARCHAR(50) COMMENT '运行状态',
    params TEXT COMMENT '设备参数(JSON)',
    image_urls TEXT COMMENT '设备图片(JSON)',
    last_heartbeat TIMESTAMP COMMENT '最后心跳时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_dev_device_code ON dev_device(device_code);
CREATE INDEX idx_dev_device_type_id ON dev_device(device_type_id);
CREATE INDEX idx_dev_device_model_id ON dev_device(model_id);
CREATE INDEX idx_dev_device_project_id ON dev_device(project_id);
CREATE INDEX idx_dev_device_status ON dev_device(status);
CREATE INDEX idx_dev_device_serial_number ON dev_device(serial_number);

-- 设备参数表
CREATE TABLE dev_device_param (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL COMMENT '设备ID',
    param_code VARCHAR(100) NOT NULL COMMENT '参数编码',
    param_name VARCHAR(200) NOT NULL COMMENT '参数名称',
    param_value VARCHAR(500) COMMENT '参数值',
    param_type VARCHAR(50) DEFAULT 'string' COMMENT '参数类型: string/number/boolean/json',
    unit VARCHAR(20) COMMENT '单位',
    min_value DECIMAL(15,4) COMMENT '最小值',
    max_value DECIMAL(15,4) COMMENT '最大值',
    is_monitored BOOLEAN DEFAULT FALSE COMMENT '是否监控',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_dev_device_param_device_id ON dev_device_param(device_id);
CREATE INDEX idx_dev_device_param_code ON dev_device_param(param_code);
CREATE UNIQUE INDEX idx_dev_device_param_unique ON dev_device_param(device_id, param_code);

-- 设备附件表
CREATE TABLE dev_device_attachment (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL COMMENT '设备ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小',
    file_type VARCHAR(50) COMMENT '文件类型',
    attachment_type VARCHAR(50) DEFAULT 'image' COMMENT '附件类型: image/document/video',
    is_primary BOOLEAN DEFAULT FALSE COMMENT '是否主图',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_dev_device_attachment_device_id ON dev_device_attachment(device_id);
CREATE INDEX idx_dev_device_attachment_type ON dev_device_attachment(attachment_type);

-- 设备日志表
CREATE TABLE dev_device_log (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL COMMENT '设备ID',
    log_type VARCHAR(50) NOT NULL COMMENT '日志类型: status/param/alert/operation',
    log_level VARCHAR(20) DEFAULT 'info' COMMENT '日志级别: debug/info/warn/error',
    log_content TEXT NOT NULL COMMENT '日志内容',
    log_data TEXT COMMENT '附加数据(JSON)',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_dev_device_log_device_id ON dev_device_log(device_id);
CREATE INDEX idx_dev_device_log_type ON dev_device_log(log_type);
CREATE INDEX idx_dev_device_log_level ON dev_device_log(log_level);
CREATE INDEX idx_dev_device_log_create_time ON dev_device_log(create_time);

-- 巡检计划表
CREATE TABLE dev_inspection_plan (
    id BIGSERIAL PRIMARY KEY,
    plan_code VARCHAR(50) NOT NULL UNIQUE COMMENT '计划编码',
    plan_name VARCHAR(200) NOT NULL COMMENT '计划名称',
    plan_type VARCHAR(50) COMMENT '计划类型: daily/weekly/monthly/quarterly/yearly',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    frequency INT DEFAULT 1 COMMENT '巡检频率',
    frequency_unit VARCHAR(20) DEFAULT 'day' COMMENT '频率单位: day/week/month',
    inspection_duration INT COMMENT '预计时长(分钟)',
    content TEXT COMMENT '巡检内容',
    standard TEXT COMMENT '巡检标准',
    status VARCHAR(20) DEFAULT 'draft' COMMENT '状态: draft/active/paused/cancelled',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_dev_inspection_plan_code ON dev_inspection_plan(plan_code);
CREATE INDEX idx_dev_inspection_plan_status ON dev_inspection_plan(status);

-- 巡检计划设备关联表
CREATE TABLE dev_inspection_plan_device (
    id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL COMMENT '计划ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_dev_inspection_plan_device_plan_id ON dev_inspection_plan_device(plan_id);
CREATE INDEX idx_dev_inspection_plan_device_device_id ON dev_inspection_plan_device(device_id);
CREATE UNIQUE INDEX idx_dev_inspection_plan_device_unique ON dev_inspection_plan_device(plan_id, device_id);

-- 巡检计划执行人关联表
CREATE TABLE dev_inspection_plan_executor (
    id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL COMMENT '计划ID',
    executor_id BIGINT NOT NULL COMMENT '执行人ID',
    executor_name VARCHAR(50) COMMENT '执行人姓名',
    is_primary BOOLEAN DEFAULT FALSE COMMENT '是否主执行人',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_dev_inspection_plan_executor_plan_id ON dev_inspection_plan_executor(plan_id);
CREATE INDEX idx_dev_inspection_plan_executor_executor_id ON dev_inspection_plan_executor(executor_id);
CREATE UNIQUE INDEX idx_dev_inspection_plan_executor_unique ON dev_inspection_plan_executor(plan_id, executor_id);

-- 巡检任务表
CREATE TABLE dev_inspection_task (
    id BIGSERIAL PRIMARY KEY,
    task_code VARCHAR(50) NOT NULL UNIQUE COMMENT '任务编码',
    plan_id BIGINT NOT NULL COMMENT '计划ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    executor_id BIGINT COMMENT '执行人ID',
    executor_name VARCHAR(50) COMMENT '执行人姓名',
    scheduled_date TIMESTAMP NOT NULL COMMENT '计划执行时间',
    actual_start_time TIMESTAMP COMMENT '实际开始时间',
    actual_end_time TIMESTAMP COMMENT '实际结束时间',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending/in_progress/completed/skipped/cancelled',
    result VARCHAR(20) COMMENT '结果: pass/fail/abnormal',
    findings TEXT COMMENT '巡检发现',
    suggestions TEXT COMMENT '处理建议',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
);

CREATE UNIQUE INDEX idx_dev_inspection_task_code ON dev_inspection_task(task_code);
CREATE INDEX idx_dev_inspection_task_plan_id ON dev_inspection_task(plan_id);
CREATE INDEX idx_dev_inspection_task_device_id ON dev_inspection_task(device_id);
CREATE INDEX idx_dev_inspection_task_executor_id ON dev_inspection_task(executor_id);
CREATE INDEX idx_dev_inspection_task_status ON dev_inspection_task(status);
CREATE INDEX idx_dev_inspection_task_scheduled_date ON dev_inspection_task(scheduled_date);

-- 巡检记录表
CREATE TABLE dev_inspection_record (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL COMMENT '巡检任务ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    executor_id BIGINT COMMENT '执行人ID',
    executor_name VARCHAR(50) COMMENT '执行人姓名',
    start_time TIMESTAMP NOT NULL COMMENT '开始时间',
    end_time TIMESTAMP COMMENT '结束时间',
    duration INT COMMENT '巡检时长(分钟)',
    location VARCHAR(500) COMMENT '实际位置',
    weather VARCHAR(50) COMMENT '天气情况',
    environment TEXT COMMENT '环境情况',
    result VARCHAR(20) COMMENT '巡检结果: pass/fail/abnormal',
    findings TEXT COMMENT '发现的问题',
    attachments TEXT COMMENT '附件(JSON)',
    signature_url VARCHAR(500) COMMENT '签名图片URL',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_dev_inspection_record_task_id ON dev_inspection_record(task_id);
CREATE INDEX idx_dev_inspection_record_device_id ON dev_inspection_record(device_id);
CREATE INDEX idx_dev_inspection_record_executor_id ON dev_inspection_record(executor_id);
CREATE INDEX idx_dev_inspection_record_result ON dev_inspection_record(result);
CREATE INDEX idx_dev_inspection_record_create_time ON dev_inspection_record(create_time);

-- 维保工单表
CREATE TABLE dev_maintenance_order (
    id BIGSERIAL PRIMARY KEY,
    order_code VARCHAR(50) NOT NULL UNIQUE COMMENT '工单编码',
    order_type VARCHAR(20) NOT NULL COMMENT '工单类型: repair/maintenance/inspection',
    priority VARCHAR(20) DEFAULT 'medium' COMMENT '优先级: low/medium/high/urgent',
    title VARCHAR(200) NOT NULL COMMENT '工单标题',
    description TEXT COMMENT '工单描述',
    device_id BIGINT COMMENT '关联设备ID',
    project_id BIGINT COMMENT '关联项目ID',
    reporter_id BIGINT COMMENT '报修人ID',
    reporter_name VARCHAR(50) COMMENT '报修人姓名',
    reporter_phone VARCHAR(20) COMMENT '报修人电话',
    assignee_id BIGINT COMMENT '指派人ID',
    assignee_name VARCHAR(50) COMMENT '指派人姓名',
    maintenance_company_id BIGINT COMMENT '维保单位ID',
    scheduled_date TIMESTAMP COMMENT '计划处理时间',
    actual_start_time TIMESTAMP COMMENT '实际开始时间',
    actual_end_time TIMESTAMP COMMENT '实际结束时间',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending/assigned/in_progress/pending_approval/approved/completed/cancelled',
    approval_status VARCHAR(20) COMMENT '审批状态',
    approval_result VARCHAR(200) COMMENT '审批结果',
    approve_time TIMESTAMP COMMENT '审批时间',
    cost DECIMAL(10,2) COMMENT '维修费用',
    cost_description TEXT COMMENT '费用说明',
    satisfaction_score INT COMMENT '满意度评分(1-5)',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE UNIQUE INDEX idx_dev_maintenance_order_code ON dev_maintenance_order(order_code);
CREATE INDEX idx_dev_maintenance_order_type ON dev_maintenance_order(order_type);
CREATE INDEX idx_dev_maintenance_order_priority ON dev_maintenance_order(priority);
CREATE INDEX idx_dev_maintenance_order_device_id ON dev_maintenance_order(device_id);
CREATE INDEX idx_dev_maintenance_order_assignee_id ON dev_maintenance_order(assignee_id);
CREATE INDEX idx_dev_maintenance_order_status ON dev_maintenance_order(status);
CREATE INDEX idx_dev_maintenance_order_create_time ON dev_maintenance_order(create_time);

-- 工单处理记录表
CREATE TABLE dev_order_record (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL COMMENT '工单ID',
    record_type VARCHAR(50) NOT NULL COMMENT '记录类型: create/assign/start/pause/resume/approve/reject/complete',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    operator_role VARCHAR(50) COMMENT '操作人角色',
    content TEXT COMMENT '处理内容',
    attachments TEXT COMMENT '附件(JSON)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

CREATE INDEX idx_dev_order_record_order_id ON dev_order_record(order_id);
CREATE INDEX idx_dev_order_record_type ON dev_order_record(record_type);
CREATE INDEX idx_dev_order_record_create_time ON dev_order_record(create_time);

-- 故障记录表
CREATE TABLE dev_fault_record (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL COMMENT '设备ID',
    fault_code VARCHAR(50) COMMENT '故障编码',
    fault_type VARCHAR(100) COMMENT '故障类型',
    fault_level VARCHAR(20) DEFAULT 'medium' COMMENT '故障等级: low/medium/high/critical',
    fault_name VARCHAR(200) NOT NULL COMMENT '故障名称',
    fault_description TEXT COMMENT '故障描述',
    fault_time TIMESTAMP NOT NULL COMMENT '故障发生时间',
    discover_time TIMESTAMP COMMENT '发现时间',
    reporter_id BIGINT COMMENT '上报人ID',
    reporter_name VARCHAR(50) COMMENT '上报人姓名',
    handler_id BIGINT COMMENT '处理人ID',
    handler_name VARCHAR(50) COMMENT '处理人姓名',
    repair_start_time TIMESTAMP COMMENT '维修开始时间',
    repair_end_time TIMESTAMP COMMENT '维修结束时间',
    repair_result VARCHAR(200) COMMENT '维修结果',
    fault_cause TEXT COMMENT '故障原因',
    solution TEXT COMMENT '解决方案',
    spare_parts TEXT COMMENT '更换备件(JSON)',
    loss_amount DECIMAL(15,2) COMMENT '损失金额',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending/diagnosis/in_progress/resolved/closed',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_dept_id BIGINT COMMENT '创建部门ID',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INTEGER DEFAULT 0 COMMENT '删除标志',
    remark VARCHAR(500) COMMENT '备注'
);

CREATE INDEX idx_dev_fault_record_device_id ON dev_fault_record(device_id);
CREATE INDEX idx_dev_fault_record_fault_type ON dev_fault_record(fault_type);
CREATE INDEX idx_dev_fault_record_level ON dev_fault_record(fault_level);
CREATE INDEX idx_dev_fault_record_status ON dev_fault_record(status);
CREATE INDEX idx_dev_fault_record_fault_time ON dev_fault_record(fault_time);
CREATE INDEX idx_dev_fault_record_create_time ON dev_fault_record(create_time);

-- ================================================
-- 初始化数据
-- ================================================

-- 插入超级管理员用户 (密码: admin123，使用BCrypt加密)
INSERT INTO sys_user (username, password, nick_name, real_name, email, phone, status, dept_id) VALUES 
('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE/TU8lHCkXLm.', '管理员', '系统管理员', 'admin@operation.com', '13800138000', '1', 1);

-- 插入部门数据
INSERT INTO sys_dept (dept_name, dept_code, parent_id, ancestors, leader, phone, status, sort) VALUES 
('总公司', 'ROOT', 0, '0', '系统管理员', '010-12345678', '1', 0),
('技术部', 'TECH', 1, '0,1', '技术总监', '010-12345679', '1', 1),
('运维部', 'OPS', 1, '0,1', '运维总监', '010-12345680', '1', 2),
('市场部', 'MARKET', 1, '0,1', '市场总监', '010-12345681', '1', 3);

-- 插入角色数据
INSERT INTO sys_role (role_name, role_key, role_sort, status) VALUES 
('超级管理员', 'super_admin', 1, '1'),
('系统管理员', 'admin', 2, '1'),
('运维工程师', 'engineer', 3, '1'),
('普通用户', 'user', 4, '1');

-- 给管理员分配角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 插入字典数据
INSERT INTO sys_dict (dict_name, dict_code, dict_type, status) VALUES 
('用户性别', 'sys_user_sex', 'string', '1'),
('设备状态', 'device_status', 'string', '1'),
('工单状态', 'order_status', 'string', '1'),
('工单类型', 'order_type', 'string', '1'),
('故障等级', 'fault_level', 'string', '1'),
('优先级', 'priority', 'string', '1'),
('巡检计划类型', 'inspection_plan_type', 'string', '1');

-- 插入字典项数据
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, sort, is_default) VALUES 
(1, '男', 'male', 1, '1'),
(1, '女', 'female', 2, '0'),
(1, '未知', 'unknown', 3, '0'),
(2, '离线', 'offline', 1, '1'),
(2, '在线', 'online', 2, '0'),
(2, '运行中', 'running', 3, '0'),
(2, '故障', 'fault', 4, '0'),
(2, '维护中', 'maintenance', 5, '0'),
(3, '待处理', 'pending', 1, '1'),
(3, '已指派', 'assigned', 2, '0'),
(3, '处理中', 'in_progress', 3, '0'),
(3, '待审批', 'pending_approval', 4, '0'),
(3, '已审批', 'approved', 5, '0'),
(3, '已完成', 'completed', 6, '0'),
(3, '已取消', 'cancelled', 7, '0'),
(4, '维修', 'repair', 1, '1'),
(4, '保养', 'maintenance', 2, '0'),
(4, '巡检', 'inspection', 3, '0'),
(5, '轻微', 'low', 1, '1'),
(5, '一般', 'medium', 2, '0'),
(5, '严重', 'high', 3, '0'),
(5, '紧急', 'critical', 4, '0'),
(6, '低', 'low', 1, '1'),
(6, '中', 'medium', 2, '0'),
(6, '高', 'high', 3, '0'),
(6, '紧急', 'urgent', 4, '0'),
(7, '日常巡检', 'daily', 1, '1'),
(7, '周巡检', 'weekly', 2, '0'),
(7, '月度巡检', 'monthly', 3, '0'),
(7, '季度巡检', 'quarterly', 4, '0'),
(7, '年度巡检', 'yearly', 5, '0');
