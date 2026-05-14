package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_maintenance_company")
public class MaintenanceCompany extends BaseEntity {

    private Long projectId;

    private String code;

    private String name;

    private String contact;

    private String phone;

    private String address;
}
