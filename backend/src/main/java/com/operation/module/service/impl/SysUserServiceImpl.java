package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysUser;
import com.operation.module.entity.SysUserRole;
import com.operation.module.mapper.SysUserMapper;
import com.operation.module.mapper.SysUserRoleMapper;
import com.operation.module.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public IPage<SysUser> selectUserPage(Integer current, Integer size, String username,
                                          String nickname, String status, Long deptId) {
        Page<SysUser> page = new Page<>(current, size);
        return sysUserMapper.selectUserPage(page, username, nickname, status, deptId);
    }
    
    @Override
    public SysUser selectUserById(Long id) {
        return sysUserMapper.selectById(id);
    }
    
    @Override
    public SysUser selectUserByUsername(String username) {
        return sysUserMapper.selectUserByUsername(username);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(SysUser user) {
        user.setPassword(passwordEncoder.encode("123456"));
        int result = sysUserMapper.insert(user);
        if (result > 0 && user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), user.getRoleIds());
        }
        return result > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUser user) {
        int result = sysUserMapper.updateById(user);
        if (result > 0 && user.getRoleIds() != null) {
            sysUserRoleMapper.deleteUserRoles(user.getId());
            if (!user.getRoleIds().isEmpty()) {
                assignRoles(user.getId(), user.getRoleIds());
            }
        }
        return result > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        return sysUserMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long id, String password) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(password));
        return sysUserMapper.updateById(user) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        return sysUserMapper.updateById(user) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAvatar(Long id, String avatar) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setAvatar(avatar);
        return sysUserMapper.updateById(user) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRoles(Long userId, String roleIds) {
        List<Long> roleIdList = Arrays.stream(roleIds.split(","))
                .map(Long::parseLong)
                .toList();
        return sysUserMapper.insertUserRoles(userId, roleIdList) > 0;
    }
}
