package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.operation.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("prj_project_stage")
public class ProjectStage extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("project_id")
    private Long projectId;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("sort")
    private Integer sort;

    @TableField("status")
    private String status;
}
