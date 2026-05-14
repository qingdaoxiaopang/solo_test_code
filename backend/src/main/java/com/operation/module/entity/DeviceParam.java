package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_device_param")
public class DeviceParam extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long deviceId;

    private String paramName;

    private String paramCode;

    private String paramType;

    private String paramUnit;

    private BigDecimal normalMin;

    private BigDecimal normalMax;

    private String defaultValue;

    private Integer sortOrder;

    private String description;

    private String enabledStatus;
}
