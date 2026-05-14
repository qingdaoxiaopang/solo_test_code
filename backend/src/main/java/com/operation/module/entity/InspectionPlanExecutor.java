package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_inspection_plan_executor")
public class InspectionPlanExecutor extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long planId;

    private Long executorId;

    private String executorName;

    private String executorPhone;

    private String department;

    private String role;

    private Integer sortOrder;

    private LocalDateTime createTime;
}
