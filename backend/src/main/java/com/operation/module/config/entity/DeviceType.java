package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_device_type")
public class DeviceType extends BaseEntity {

    private Long projectId;

    private Long parentId;

    private String code;

    private String name;

    private Integer sort;

    @TableField(exist = false)
    private java.util.List<DeviceType> children;
}
