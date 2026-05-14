package com.operation.module.entity;

public enum MemberRole {

    MANAGER("MANAGER", "项目经理"),
    MEMBER("MEMBER", "成员"),
    VIEWER("VIEWER", "查看者");

    private final String code;
    private final String desc;

    MemberRole(String code, String desc) {
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
