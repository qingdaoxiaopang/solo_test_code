package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.entity.SysUser;

public interface ISysUserService {
    
    IPage<SysUser> selectUserPage(Integer current, Integer size, String username, 
                                   String nickname, String status, Long deptId);
    
    SysUser selectUserById(Long id);
    
    SysUser selectUserByUsername(String username);
    
    boolean saveUser(SysUser user);
    
    boolean updateUser(SysUser user);
    
    boolean deleteUser(Long id);
    
    boolean resetPassword(Long id, String password);
    
    boolean updateStatus(Long id, String status);
    
    boolean updateAvatar(Long id, String avatar);
    
    boolean assignRoles(Long userId, String roleIds);
}
