package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_spare_part")
public class SparePart extends BaseEntity {

    private Long projectId;

    private Long categoryId;

    private String code;

    private String name;

    private String specification;

    private String unit;

    private BigDecimal stockQuantity;

    private BigDecimal warnQuantity;

    private BigDecimal price;
}
