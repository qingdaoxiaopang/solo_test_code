package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionPlan;
import com.operation.module.entity.InspectionPlanDevice;
import com.operation.module.entity.InspectionPlanExecutor;
import com.operation.module.service.IInspectionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inspection/plans")
public class InspectionPlanController {

    @Autowired
    private IInspectionPlanService inspectionPlanService;

    @GetMapping
    public Result<IPage<InspectionPlan>> getPlanPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String planName,
            @RequestParam(required = false) String enabledStatus,
            @RequestParam(required = false) String completedStatus) {
        Page<InspectionPlan> page = new Page<>(pageNum, pageSize);
        IPage<InspectionPlan> result = inspectionPlanService.getPlanPage(page, projectId, planName, enabledStatus, completedStatus);
        return Result.success(result);
    }

    @PostMapping
    public Result<Boolean> createPlan(@RequestBody InspectionPlan plan) {
        boolean result = inspectionPlanService.createPlan(plan);
        return Result.success(result);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updatePlan(@PathVariable Long id, @RequestBody InspectionPlan plan) {
        plan.setId(id);
        boolean result = inspectionPlanService.updatePlan(plan);
        return Result.success(result);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deletePlan(@PathVariable Long id) {
        boolean result = inspectionPlanService.deletePlan(id);
        return Result.success(result);
    }

    @PostMapping("/{id}/devices")
    public Result<Boolean> addPlanDevice(@PathVariable Long id, @RequestBody InspectionPlanDevice planDevice) {
        boolean result = inspectionPlanService.addPlanDevice(id, planDevice);
        return Result.success(result);
    }

    @DeleteMapping("/{id}/devices/{deviceId}")
    public Result<Boolean> removePlanDevice(@PathVariable Long id, @PathVariable Long deviceId) {
        boolean result = inspectionPlanService.removePlanDevice(id, deviceId);
        return Result.success(result);
    }

    @GetMapping("/{id}/devices")
    public Result<List<InspectionPlanDevice>> getPlanDevices(@PathVariable Long id) {
        List<InspectionPlanDevice> devices = inspectionPlanService.getPlanDevices(id);
        return Result.success(devices);
    }

    @PostMapping("/{id}/executors")
    public Result<Boolean> addPlanExecutor(@PathVariable Long id, @RequestBody InspectionPlanExecutor executor) {
        boolean result = inspectionPlanService.addPlanExecutor(id, executor);
        return Result.success(result);
    }

    @DeleteMapping("/{id}/executors/{executorId}")
    public Result<Boolean> removePlanExecutor(@PathVariable Long id, @PathVariable Long executorId) {
        boolean result = inspectionPlanService.removePlanExecutor(id, executorId);
        return Result.success(result);
    }

    @GetMapping("/{id}/executors")
    public Result<List<InspectionPlanExecutor>> getPlanExecutors(@PathVariable Long id) {
        List<InspectionPlanExecutor> executors = inspectionPlanService.getPlanExecutors(id);
        return Result.success(executors);
    }
}
