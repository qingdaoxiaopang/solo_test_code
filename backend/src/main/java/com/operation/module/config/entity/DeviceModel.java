package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_device_model")
public class DeviceModel extends BaseEntity {

    private Long projectId;

    private String code;

    private String name;

    private String deviceTypeCode;

    private String manufacturerCode;
}
