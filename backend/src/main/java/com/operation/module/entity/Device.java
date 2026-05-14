package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_device")
public class Device extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private String deviceName;

    private String deviceCode;

    private String deviceType;

    private String deviceModel;

    private String manufacturer;

    private String supplier;

    private String serialNumber;

    private String installationLocation;

    private Double longitude;

    private Double latitude;

    private String status;

    private LocalDateTime purchaseDate;

    private LocalDateTime installDate;

    private LocalDateTime commissioningDate;

    private LocalDateTime warrantyExpiry;

    private String responsiblePerson;

    private String contactPhone;

    private String technicalSpecs;

    private String description;

    private String qrCode;

    private String mainPhoto;

    private String sitePhotos;

    private Integer inspectionCycle;

    private String inspectionStandard;

    private Double ratedPower;

    private String voltageLevel;

    private String runningStatus;

    private Double operatingHours;

    private LocalDateTime lastMaintenanceDate;

    private LocalDateTime nextMaintenanceDate;

    private String healthScore;

    private String enabledStatus;
}
