package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_topic_device_type")
public class TopicDeviceType extends BaseEntity {

    private Long topicId;

    private Long deviceTypeId;
}
