package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
    
    IPage<SysLog> selectLogPage(Page<SysLog> page, @Param("logType") String logType,
                                @Param("operationType") String operationType,
                                @Param("username") String username,
                                @Param("status") String status,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime);
}
