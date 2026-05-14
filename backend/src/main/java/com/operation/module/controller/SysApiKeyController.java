package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysApiKey;
import com.operation.module.service.ISysApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/api-keys")
@SaCheckLogin
public class SysApiKeyController {
    
    @Autowired
    private ISysApiKeyService sysApiKeyService;
    
    @GetMapping
    @SaCheckPermission("system:apiKey:list")
    public Result<IPage<SysApiKey>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyName,
            @RequestParam(required = false) String status) {
        IPage<SysApiKey> page = sysApiKeyService.selectApiKeyPage(current, size, keyName, status);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:apiKey:list")
    public Result<SysApiKey> getById(@PathVariable Long id) {
        SysApiKey apiKey = sysApiKeyService.selectApiKeyById(id);
        return Result.success(apiKey);
    }
    
    @PostMapping
    @SaCheckPermission("system:apiKey:add")
    public Result<Void> save(@RequestBody SysApiKey apiKey) {
        sysApiKeyService.saveApiKey(apiKey);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:apiKey:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysApiKey apiKey) {
        apiKey.setId(id);
        sysApiKeyService.updateApiKey(apiKey);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:apiKey:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysApiKeyService.deleteApiKey(id);
        return Result.success();
    }
    
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:apiKey:edit")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        sysApiKeyService.updateStatus(id, status);
        return Result.success();
    }
    
    @PutMapping("/{id}/reset-secret")
    @SaCheckPermission("system:apiKey:resetSecret")
    public Result<Void> resetSecret(@PathVariable Long id) {
        sysApiKeyService.resetSecret(id);
        return Result.success();
    }
    
    @GetMapping("/{id}/usage")
    @SaCheckPermission("system:apiKey:list")
    public Result<SysApiKey> getUsage(@PathVariable Long id) {
        SysApiKey apiKey = sysApiKeyService.selectApiKeyById(id);
        return Result.success(apiKey);
    }
}
