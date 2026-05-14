package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.entity.SysRole;

import java.util.List;

public interface ISysRoleService {
    
    IPage<SysRole> selectRolePage(Integer current, Integer size, String roleName,
                                   String roleCode, String status);
    
    SysRole selectRoleById(Long id);
    
    List<SysRole> selectRolesByUserId(Long userId);
    
    List<Long> selectRoleIdsByUserId(Long userId);
    
    boolean saveRole(SysRole role);
    
    boolean updateRole(SysRole role);
    
    boolean deleteRole(Long id);
    
    boolean updateStatus(Long id, String status);
    
    List<Long> selectPermissionIdsByRoleId(Long roleId);
    
    boolean assignPermissions(Long roleId, String permissionIds);
}
