package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysDictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    
    IPage<SysDictItem> selectDictItemPage(Page<SysDictItem> page, @Param("dictId") Long dictId,
                                          @Param("itemText") String itemText, @Param("status") String status);
    
    List<SysDictItem> selectDictItemsByDictId(@Param("dictId") Long dictId);
    
    List<SysDictItem> selectDictItemsByDictCode(@Param("dictCode") String dictCode);
}
