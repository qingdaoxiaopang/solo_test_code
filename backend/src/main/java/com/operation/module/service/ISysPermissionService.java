package com.operation.module.service;

import com.operation.module.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService {
    
    List<SysPermission> selectPermissionTree();
    
    List<SysPermission> selectPermissionsByRoleId(Long roleId);
    
    List<SysPermission> selectPermissionsByUserId(Long userId);
    
    List<String> selectPermissionCodesByUserId(Long userId);
    
    SysPermission selectPermissionById(Long id);
    
    boolean savePermission(SysPermission permission);
    
    boolean updatePermission(SysPermission permission);
    
    boolean deletePermission(Long id);
    
    List<Long> selectChildPermissionIds(Long parentId);
}
