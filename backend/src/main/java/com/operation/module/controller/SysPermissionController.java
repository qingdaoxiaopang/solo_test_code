package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysPermission;
import com.operation.module.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@SaCheckLogin
public class SysPermissionController {
    
    @Autowired
    private ISysPermissionService sysPermissionService;
    
    @GetMapping("/tree")
    @SaCheckPermission("system:permission:list")
    public Result<List<SysPermission>> tree() {
        List<SysPermission> tree = sysPermissionService.selectPermissionTree();
        return Result.success(tree);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:permission:list")
    public Result<SysPermission> getById(@PathVariable Long id) {
        SysPermission permission = sysPermissionService.selectPermissionById(id);
        return Result.success(permission);
    }
    
    @PostMapping
    @SaCheckPermission("system:permission:add")
    public Result<Void> save(@RequestBody SysPermission permission) {
        sysPermissionService.savePermission(permission);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:permission:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysPermission permission) {
        permission.setId(id);
        sysPermissionService.updatePermission(permission);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:permission:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysPermissionService.deletePermission(id);
        return Result.success();
    }
}
