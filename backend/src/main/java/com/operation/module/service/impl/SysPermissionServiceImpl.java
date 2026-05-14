package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.module.entity.SysPermission;
import com.operation.module.mapper.SysPermissionMapper;
import com.operation.module.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {
    
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    
    @Override
    public List<SysPermission> selectPermissionTree() {
        return sysPermissionMapper.selectPermissionTree();
    }
    
    @Override
    public List<SysPermission> selectPermissionsByRoleId(Long roleId) {
        return sysPermissionMapper.selectPermissionsByRoleId(roleId);
    }
    
    @Override
    public List<SysPermission> selectPermissionsByUserId(Long userId) {
        return sysPermissionMapper.selectPermissionsByUserId(userId);
    }
    
    @Override
    public List<String> selectPermissionCodesByUserId(Long userId) {
        return sysPermissionMapper.selectPermissionCodesByUserId(userId);
    }
    
    @Override
    public SysPermission selectPermissionById(Long id) {
        return sysPermissionMapper.selectById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePermission(SysPermission permission) {
        return sysPermissionMapper.insert(permission) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePermission(SysPermission permission) {
        return sysPermissionMapper.updateById(permission) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(Long id) {
        List<Long> childIds = selectChildPermissionIds(id);
        if (!childIds.isEmpty()) {
            sysPermissionMapper.deleteBatchIds(childIds);
        }
        return sysPermissionMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<Long> selectChildPermissionIds(Long parentId) {
        List<Long> result = new ArrayList<>();
        collectChildIds(parentId, result);
        return result;
    }
    
    private void collectChildIds(Long parentId, List<Long> result) {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getParentId, parentId);
        List<SysPermission> children = sysPermissionMapper.selectList(wrapper);
        for (SysPermission child : children) {
            result.add(child.getId());
            collectChildIds(child.getId(), result);
        }
    }
}
