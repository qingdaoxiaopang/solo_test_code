package com.operation.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.common.entity.BaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseMapperPlus<T extends BaseEntity> extends BaseMapper<T> {
    
    default IPage<T> selectPageVO(Page<T> page) {
        return selectPage(page, null);
    }
    
    default List<T> selectListVO() {
        return selectList(null);
    }
}
