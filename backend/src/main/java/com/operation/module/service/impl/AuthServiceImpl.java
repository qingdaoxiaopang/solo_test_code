package com.operation.module.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.operation.common.entity.LoginUser;
import com.operation.module.entity.SysUser;
import com.operation.module.service.IAuthService;
import com.operation.module.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {
    
    @Autowired
    private ISysUserService sysUserService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Map<String, Object> login(String username, String password) {
        SysUser user = sysUserService.selectUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        if (!"0".equals(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        StpUtil.login(user.getId());
        StpUtil.getSession().set("user", user);
        
        List<String> permissionCodes = List.of("system:user:list", "system:user:add");
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", StpUtil.getTokenValue());
        result.put("userInfo", getLoginUser(user));
        result.put("permissions", permissionCodes);
        
        user.setLastLoginTime(LocalDateTime.now());
        sysUserService.updateUser(user);
        
        return result;
    }
    
    @Override
    public boolean logout() {
        StpUtil.logout();
        return true;
    }
    
    @Override
    public LoginUser getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserService.selectUserById(userId);
        return getLoginUser(user);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(String oldPassword, String newPassword) {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserService.selectUserById(userId);
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        return sysUserService.resetPassword(userId, newPassword);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long userId, String password) {
        return sysUserService.resetPassword(userId, password);
    }
    
    private LoginUser getLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setDeptId(user.getDeptId());
        return loginUser;
    }
}
