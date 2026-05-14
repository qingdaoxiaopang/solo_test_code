package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysRole;
import com.operation.module.mapper.SysRoleMapper;
import com.operation.module.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Override
    public IPage<SysRole> selectRolePage(Integer current, Integer size, String roleName,
                                          String roleCode, String status) {
        Page<SysRole> page = new Page<>(current, size);
        return sysRoleMapper.selectRolePage(page, roleName, roleCode, status);
    }
    
    @Override
    public SysRole selectRoleById(Long id) {
        return sysRoleMapper.selectById(id);
    }
    
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }
    
    @Override
    public List<Long> selectRoleIdsByUserId(Long userId) {
        return sysRoleMapper.selectRoleIdsByUserId(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(SysRole role) {
        return sysRoleMapper.insert(role) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SysRole role) {
        return sysRoleMapper.updateById(role) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(Long id) {
        return sysRoleMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String status) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setStatus(status);
        return sysRoleMapper.updateById(role) > 0;
    }
    
    @Override
    public List<Long> selectPermissionIdsByRoleId(Long roleId) {
        return sysRoleMapper.selectRolePermissions(roleId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignPermissions(Long roleId, String permissionIds) {
        sysRoleMapper.deleteRolePermissions(roleId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<Long> permissionIdList = Arrays.stream(permissionIds.split(","))
                    .map(Long::parseLong)
                    .toList();
            return sysRoleMapper.insertRolePermissions(roleId, permissionIdList) > 0;
        }
        return true;
    }
}
