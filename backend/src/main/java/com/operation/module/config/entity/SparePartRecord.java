package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_spare_part_record")
public class SparePartRecord extends BaseEntity {

    private Long projectId;

    private Long sparePartId;

    private BigDecimal quantity;

    private String status;

    private Long applicantId;

    private Long approverId;

    private LocalDateTime approveTime;

    private String approveRemark;

    @TableField(exist = false)
    private String sparePartName;

    @TableField(exist = false)
    private String applicantName;

    @TableField(exist = false)
    private String approverName;
}
