package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_topic")
public class Topic extends BaseEntity {

    private Long projectId;

    private String code;

    private String name;

    private String description;

    private String status;
}
