package com.operation.module.entity;

public enum ProjectStatus {

    DRAFT("DRAFT", "草稿"),
    PENDING("PENDING", "待启动"),
    IN_PROGRESS("IN_PROGRESS", "进行中"),
    SUSPENDED("SUSPENDED", "已暂停"),
    COMPLETED("COMPLETED", "已完成"),
    CANCELLED("CANCELLED", "已取消");

    private final String code;
    private final String desc;

    ProjectStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
