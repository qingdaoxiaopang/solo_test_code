package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_fault_record")
public class FaultRecord extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private Long deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceLocation;

    private String faultNo;

    private String faultType;

    private String faultLevel;

    private String faultStatus;

    private String faultTitle;

    private String faultDescription;

    private String faultPhotos;

    private LocalDateTime faultTime;

    private Long reporterId;

    private String reporterName;

    private String reporterPhone;

    private LocalDateTime reportTime;

    private Long handlerId;

    private String handlerName;

    private LocalDateTime handleTime;

    private String handleResult;

    private String handlePhotos;

    private LocalDateTime resolveTime;

    private Long resolverId;

    private String resolverName;

    private String resolution;

    private String resolutionPhotos;

    private String sparePartsUsed;

    private BigDecimal repairCost;

    private LocalDateTime closeTime;

    private Long closerId;

    private String closerName;

    private String closeRemark;

    private Long maintenanceOrderId;

    private String relatedKnowledgeIds;

    private String remark;
}
