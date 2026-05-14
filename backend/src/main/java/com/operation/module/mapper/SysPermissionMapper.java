package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    
    List<SysPermission> selectPermissionTree();
    
    List<SysPermission> selectPermissionsByRoleId(@Param("roleId") Long roleId);
    
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);
    
    List<SysPermission> selectPermissionsByUserId(@Param("userId") Long userId);
    
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}
