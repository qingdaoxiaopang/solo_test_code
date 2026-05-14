package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysRole;
import com.operation.module.entity.SysPermission;
import com.operation.module.service.ISysRoleService;
import com.operation.module.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
@SaCheckLogin
public class SysRoleController {
    
    @Autowired
    private ISysRoleService sysRoleService;
    
    @Autowired
    private ISysPermissionService sysPermissionService;
    
    @GetMapping
    @SaCheckPermission("system:role:list")
    public Result<IPage<SysRole>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String roleCode,
            @RequestParam(required = false) String status) {
        IPage<SysRole> page = sysRoleService.selectRolePage(current, size, roleName, roleCode, status);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:role:list")
    public Result<SysRole> getById(@PathVariable Long id) {
        SysRole role = sysRoleService.selectRoleById(id);
        return Result.success(role);
    }
    
    @PostMapping
    @SaCheckPermission("system:role:add")
    public Result<Void> save(@RequestBody SysRole role) {
        sysRoleService.saveRole(role);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:role:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        sysRoleService.updateRole(role);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:role:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysRoleService.deleteRole(id);
        return Result.success();
    }
    
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:role:edit")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        sysRoleService.updateStatus(id, status);
        return Result.success();
    }
    
    @GetMapping("/{id}/permissions")
    @SaCheckPermission("system:role:list")
    public Result<List<SysPermission>> getPermissions(@PathVariable Long id) {
        List<SysPermission> permissions = sysPermissionService.selectPermissionsByRoleId(id);
        return Result.success(permissions);
    }
    
    @GetMapping("/tree/permissions")
    @SaCheckPermission("system:role:list")
    public Result<List<SysPermission>> getPermissionTree() {
        List<SysPermission> tree = sysPermissionService.selectPermissionTree();
        return Result.success(tree);
    }
    
    @PutMapping("/{id}/permissions")
    @SaCheckPermission("system:role:edit")
    public Result<Void> assignPermissions(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String permissionIds = params.get("permissionIds");
        sysRoleService.assignPermissions(id, permissionIds);
        return Result.success();
    }
}
