package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.operation.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("prj_project")
public class Project extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("manager_name")
    private String managerName;

    @TableField("contact_phone")
    private String contactPhone;

    @TableField("region_level")
    private String regionLevel;

    @TableField("region_name")
    private String regionName;

    @TableField("region_code")
    private String regionCode;

    @TableField("address")
    private String address;

    @TableField("longitude")
    private Double longitude;

    @TableField("latitude")
    private Double latitude;

    @TableField("status")
    private String status;

    @TableField("stage")
    private String stage;

    @TableField("start_date")
    private LocalDate startDate;

    @TableField("end_date")
    private LocalDate endDate;
}
