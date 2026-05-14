package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysUser;
import com.operation.module.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@SaCheckLogin
public class SysUserController {
    
    @Autowired
    private ISysUserService sysUserService;
    
    @GetMapping
    @SaCheckPermission("system:user:list")
    public Result<IPage<SysUser>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long deptId) {
        IPage<SysUser> page = sysUserService.selectUserPage(current, size, username, nickname, status, deptId);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:user:list")
    public Result<SysUser> getById(@PathVariable Long id) {
        SysUser user = sysUserService.selectUserById(id);
        return Result.success(user);
    }
    
    @PostMapping
    @SaCheckPermission("system:user:add")
    public Result<Void> save(@RequestBody SysUser user) {
        sysUserService.saveUser(user);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:user:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        sysUserService.updateUser(user);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:user:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success();
    }
    
    @PutMapping("/{id}/password")
    @SaCheckPermission("system:user:resetPwd")
    public Result<Void> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String password = params.get("password");
        sysUserService.resetPassword(id, password);
        return Result.success();
    }
    
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:user:edit")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        sysUserService.updateStatus(id, status);
        return Result.success();
    }
    
    @PostMapping("/{id}/avatar")
    @SaCheckPermission("system:user:edit")
    public Result<Void> updateAvatar(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String avatar = params.get("avatar");
        sysUserService.updateAvatar(id, avatar);
        return Result.success();
    }
    
    @PutMapping("/{id}/roles")
    @SaCheckPermission("system:user:edit")
    public Result<Void> assignRoles(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String roleIds = params.get("roleIds");
        sysUserService.assignRoles(id, roleIds);
        return Result.success();
    }
}
