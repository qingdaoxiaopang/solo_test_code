package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysLog;
import com.operation.module.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
@SaCheckLogin
public class SysLogController {
    
    @Autowired
    private ISysLogService sysLogService;
    
    @GetMapping
    @SaCheckPermission("system:log:list")
    public Result<IPage<SysLog>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        IPage<SysLog> page = sysLogService.selectLogPage(current, size, logType, operationType, username, status, startTime, endTime);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:log:list")
    public Result<SysLog> getById(@PathVariable Long id) {
        SysLog log = sysLogService.selectLogById(id);
        return Result.success(log);
    }
    
    @GetMapping("/export")
    @SaCheckPermission("system:log:export")
    public void export(
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletResponse response) {
        SysLog log = new SysLog();
        log.setLogType(logType);
        log.setOperationType(operationType);
        log.setUsername(username);
        log.setStatus(status);
        List<SysLog> logs = sysLogService.selectLogList(log);
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:log:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysLogService.deleteLog(id);
        return Result.success();
    }
    
    @DeleteMapping("/batch")
    @SaCheckPermission("system:log:del")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        sysLogService.deleteLogs(ids);
        return Result.success();
    }
    
    @DeleteMapping("/clear")
    @SaCheckPermission("system:log:del")
    public Result<Void> clear() {
        sysLogService.clearLogs();
        return Result.success();
    }
}
