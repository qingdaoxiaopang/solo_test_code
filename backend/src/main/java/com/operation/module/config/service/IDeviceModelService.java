package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.DeviceModel;

public interface IDeviceModelService extends IService<DeviceModel> {

    boolean saveDeviceModel(DeviceModel deviceModel);

    boolean updateDeviceModel(DeviceModel deviceModel);

    boolean deleteDeviceModel(Long id);

    boolean hasRelatedDevices(Long id);
}
