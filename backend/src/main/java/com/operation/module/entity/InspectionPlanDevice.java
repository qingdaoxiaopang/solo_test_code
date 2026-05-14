package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_inspection_plan_device")
public class InspectionPlanDevice extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long planId;

    private Long deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceLocation;

    private Integer sortOrder;

    private String remark;

    private LocalDateTime createTime;
}
