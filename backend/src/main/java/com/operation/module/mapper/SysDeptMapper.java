package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    
    List<SysDept> selectDeptTree();
    
    List<SysDept> selectDeptsByParentId(@Param("parentId") Long parentId);
    
    List<Long> selectDeptIdsByParentId(@Param("parentId") Long parentId);
}
