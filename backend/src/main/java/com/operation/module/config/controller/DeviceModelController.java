package com.operation.module.config.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.service.IDeviceModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/device-models")
public class DeviceModelController {

    private final IDeviceModelService deviceModelService;

    public DeviceModelController(IDeviceModelService deviceModelService) {
        this.deviceModelService = deviceModelService;
    }

    @GetMapping
    public Result<List<DeviceModel>> getDeviceModelList() {
        Long projectId = UserUtils.getProjectId();
        LambdaQueryWrapper<DeviceModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceModel::getProjectId, projectId)
               .orderByDesc(DeviceModel::getCreateTime);
        List<DeviceModel> list = deviceModelService.list(wrapper);
        return Result.success(list);
    }

    @PostMapping
    public Result<Boolean> createDeviceModel(@RequestBody DeviceModel deviceModel) {
        boolean success = deviceModelService.saveDeviceModel(deviceModel);
        return Result.success(success);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateDeviceModel(@PathVariable Long id, @RequestBody DeviceModel deviceModel) {
        deviceModel.setId(id);
        boolean success = deviceModelService.updateDeviceModel(deviceModel);
        return Result.success(success);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDeviceModel(@PathVariable Long id) {
        boolean success = deviceModelService.deleteDeviceModel(id);
        return Result.success(success);
    }
}
