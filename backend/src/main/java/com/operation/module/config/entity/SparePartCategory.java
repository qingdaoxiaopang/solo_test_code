package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_spare_part_category")
public class SparePartCategory extends BaseEntity {

    private Long projectId;

    private Long parentId;

    private String name;

    private String code;

    private Integer sort;

    @TableField(exist = false)
    private List<SparePartCategory> children;
}
