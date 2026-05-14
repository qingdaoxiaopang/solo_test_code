package com.operation.module.config.controller;

import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.DeviceType;
import com.operation.module.config.service.IDeviceTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/device-types")
public class DeviceTypeController {

    private final IDeviceTypeService deviceTypeService;

    public DeviceTypeController(IDeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @GetMapping
    public Result<List<DeviceType>> getDeviceTypeTree() {
        Long projectId = UserUtils.getProjectId();
        List<DeviceType> tree = deviceTypeService.getDeviceTypeTree(projectId);
        return Result.success(tree);
    }

    @PostMapping
    public Result<Boolean> createDeviceType(@RequestBody DeviceType deviceType) {
        boolean success = deviceTypeService.saveDeviceType(deviceType);
        return Result.success(success);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateDeviceType(@PathVariable Long id, @RequestBody DeviceType deviceType) {
        deviceType.setId(id);
        boolean success = deviceTypeService.updateDeviceType(deviceType);
        return Result.success(success);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDeviceType(@PathVariable Long id) {
        boolean success = deviceTypeService.deleteDeviceType(id);
        return Result.success(success);
    }
}
