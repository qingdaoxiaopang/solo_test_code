package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.DeviceType;
import java.util.List;

public interface IDeviceTypeService extends IService<DeviceType> {

    List<DeviceType> getDeviceTypeTree(Long projectId);

    boolean saveDeviceType(DeviceType deviceType);

    boolean updateDeviceType(DeviceType deviceType);

    boolean deleteDeviceType(Long id);

    boolean hasChildren(Long id);
}
