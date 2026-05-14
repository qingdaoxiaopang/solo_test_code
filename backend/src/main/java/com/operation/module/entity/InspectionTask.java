package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_inspection_task")
public class InspectionTask extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long planId;

    private Long projectId;

    private String taskName;

    private String taskType;

    private LocalDateTime scheduledStartTime;

    private LocalDateTime scheduledEndTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private String taskStatus;

    private String priority;

    private Long assignedTo;

    private String assignedName;

    private String inspectionContent;

    private String remark;

    private LocalDateTime createTime;
}
