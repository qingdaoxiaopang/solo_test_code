package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    
    IPage<SysRole> selectRolePage(Page<SysRole> page, @Param("roleName") String roleName,
                                   @Param("roleCode") String roleCode, @Param("status") String status);
    
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);
    
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
    
    int deleteRolePermissions(@Param("roleId") Long roleId);
    
    int insertRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
