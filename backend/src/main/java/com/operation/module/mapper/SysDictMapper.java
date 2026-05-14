package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
    
    IPage<SysDict> selectDictPage(Page<SysDict> page, @Param("dictName") String dictName,
                                  @Param("dictCode") String dictCode, @Param("status") String status);
}
