package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_order_record")
public class OrderRecord extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String recordType;

    private String recordContent;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime operateTime;

    private String remark;

    private String attachments;
}
