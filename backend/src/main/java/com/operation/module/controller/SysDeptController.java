package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysDept;
import com.operation.module.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depts")
@SaCheckLogin
public class SysDeptController {
    
    @Autowired
    private ISysDeptService sysDeptService;
    
    @GetMapping("/tree")
    @SaCheckPermission("system:dept:list")
    public Result<List<SysDept>> tree() {
        List<SysDept> tree = sysDeptService.selectDeptTree();
        return Result.success(tree);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:dept:list")
    public Result<SysDept> getById(@PathVariable Long id) {
        SysDept dept = sysDeptService.selectDeptById(id);
        return Result.success(dept);
    }
    
    @PostMapping
    @SaCheckPermission("system:dept:add")
    public Result<Void> save(@RequestBody SysDept dept) {
        sysDeptService.saveDept(dept);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:dept:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysDept dept) {
        dept.setId(id);
        sysDeptService.updateDept(dept);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:dept:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysDeptService.deleteDept(id);
        return Result.success();
    }
}
