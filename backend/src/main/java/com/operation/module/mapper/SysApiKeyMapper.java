package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysApiKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysApiKeyMapper extends BaseMapper<SysApiKey> {
    
    IPage<SysApiKey> selectApiKeyPage(Page<SysApiKey> page, @Param("keyName") String keyName,
                                      @Param("status") String status);
    
    List<SysApiKey> selectApiKeysByUserId(@Param("userId") Long userId);
}
