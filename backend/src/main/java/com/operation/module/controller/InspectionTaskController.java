package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionTask;
import com.operation.module.entity.InspectionRecord;
import com.operation.module.service.IInspectionTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inspection")
public class InspectionTaskController {

    @Autowired
    private IInspectionTaskService inspectionTaskService;

    @GetMapping("/tasks")
    public Result<IPage<InspectionTask>> getTaskPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) String taskStatus,
            @RequestParam(required = false) Long assignedTo) {
        Page<InspectionTask> page = new Page<>(pageNum, pageSize);
        IPage<InspectionTask> result = inspectionTaskService.getTaskPage(page, projectId, taskName, taskStatus, assignedTo);
        return Result.success(result);
    }

    @PostMapping("/tasks/{id}/execute")
    public Result<Boolean> executeTask(@PathVariable Long id, @RequestBody InspectionRecord record) {
        boolean result = inspectionTaskService.executeTask(id, record);
        return Result.success(result);
    }

    @GetMapping("/records")
    public Result<IPage<InspectionRecord>> getRecordPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) Long inspectorId,
            @RequestParam(required = false) String inspectionResult,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Page<InspectionRecord> page = new Page<>(pageNum, pageSize);
        IPage<InspectionRecord> result = inspectionTaskService.getRecordPage(page, projectId, deviceId, inspectorId, inspectionResult, startTime, endTime);
        return Result.success(result);
    }

    @GetMapping("/records/{id}")
    public Result<InspectionRecord> getRecordById(@PathVariable Long id) {
        InspectionRecord record = inspectionTaskService.getRecordById(id);
        return Result.success(record);
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getInspectionStats(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Map<String, Object> stats = inspectionTaskService.getInspectionStats(projectId, startTime, endTime);
        return Result.success(stats);
    }
}
