package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_inspection_record")
public class InspectionRecord extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private Long planId;

    private Long deviceId;

    private String deviceName;

    private String deviceCode;

    private Long inspectorId;

    private String inspectorName;

    private LocalDateTime inspectionTime;

    private String inspectionResult;

    private String checkItems;

    private String checkResult;

    private String anomalyDescription;

    private String photoUrls;

    private String remark;

    private String signatureUrl;

    private String gpsLocation;

    private Double longitude;

    private Double latitude;
}
