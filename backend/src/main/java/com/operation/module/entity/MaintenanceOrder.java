package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_maintenance_order")
public class MaintenanceOrder extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private String orderNo;

    private String orderType;

    private String title;

    private Long deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceLocation;

    private String priority;

    private String orderStatus;

    private String faultType;

    private String faultDescription;

    private String faultPhotos;

    private Long reporterId;

    private String reporterName;

    private LocalDateTime reportTime;

    private Long assigneeId;

    private String assigneeName;

    private String assigneePhone;

    private LocalDateTime assignTime;

    private LocalDateTime scheduledStartTime;

    private LocalDateTime scheduledEndTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private String maintenanceType;

    private String maintenanceContent;

    private String maintenancePhotos;

    private String spareParts;

    private BigDecimal maintenanceCost;

    private String remark;

    private Long approverId;

    private String approverName;

    private LocalDateTime approveTime;

    private String approveRemark;
}
