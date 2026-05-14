package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_inspection_plan")
public class InspectionPlan extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private String planName;

    private String planType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String enabledStatus;

    private String completedStatus;

    private Integer deviceCount;

    private Integer executorCount;

    private String inspectionContent;

    private String inspectionStandard;

    private String remark;

    private Long createdBy;

    private LocalDateTime createTime;

    private Long updatedBy;

    private LocalDateTime updateTime;
}
