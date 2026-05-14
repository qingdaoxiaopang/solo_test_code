package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.module.entity.SysDept;
import com.operation.module.mapper.SysDeptMapper;
import com.operation.module.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl implements ISysDeptService {
    
    @Autowired
    private SysDeptMapper sysDeptMapper;
    
    @Override
    public List<SysDept> selectDeptTree() {
        return sysDeptMapper.selectDeptTree();
    }
    
    @Override
    public List<SysDept> selectDeptsByParentId(Long parentId) {
        return sysDeptMapper.selectDeptsByParentId(parentId);
    }
    
    @Override
    public SysDept selectDeptById(Long id) {
        return sysDeptMapper.selectById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDept(SysDept dept) {
        return sysDeptMapper.insert(dept) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(SysDept dept) {
        return sysDeptMapper.updateById(dept) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDept(Long id) {
        List<Long> childIds = selectChildDeptIds(id);
        if (!childIds.isEmpty()) {
            sysDeptMapper.deleteBatchIds(childIds);
        }
        return sysDeptMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<Long> selectChildDeptIds(Long parentId) {
        List<Long> result = new ArrayList<>();
        collectChildIds(parentId, result);
        return result;
    }
    
    private void collectChildIds(Long parentId, List<Long> result) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getParentId, parentId);
        List<SysDept> children = sysDeptMapper.selectList(wrapper);
        for (SysDept child : children) {
            result.add(child.getId());
            collectChildIds(child.getId(), result);
        }
    }
}
