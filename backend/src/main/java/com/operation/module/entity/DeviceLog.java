package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_device_log")
public class DeviceLog extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long deviceId;

    private String logType;

    private String operationType;

    private String operationContent;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime operateTime;

    private String oldValue;

    private String newValue;

    private String remark;
}
