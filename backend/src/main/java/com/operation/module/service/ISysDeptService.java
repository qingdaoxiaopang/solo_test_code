package com.operation.module.service;

import com.operation.module.entity.SysDept;

import java.util.List;

public interface ISysDeptService {
    
    List<SysDept> selectDeptTree();
    
    List<SysDept> selectDeptsByParentId(Long parentId);
    
    SysDept selectDeptById(Long id);
    
    boolean saveDept(SysDept dept);
    
    boolean updateDept(SysDept dept);
    
    boolean deleteDept(Long id);
    
    List<Long> selectChildDeptIds(Long parentId);
}
