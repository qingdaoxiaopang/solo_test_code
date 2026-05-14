package com.operation.module.service;

import com.operation.common.entity.LoginUser;
import com.operation.module.entity.SysUser;

import java.util.Map;

public interface IAuthService {
    
    Map<String, Object> login(String username, String password);
    
    boolean logout();
    
    LoginUser getUserInfo();
    
    boolean updatePassword(String oldPassword, String newPassword);
    
    boolean resetPassword(Long userId, String password);
}
