package com.operation.module.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.config.entity.Topic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
}
