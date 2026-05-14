package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.mapper.DeviceModelMapper;
import com.operation.module.config.service.IDeviceModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceModelServiceImpl extends ServiceImpl<DeviceModelMapper, DeviceModel> implements IDeviceModelService {

    @Override
    @Transactional
    public boolean saveDeviceModel(DeviceModel deviceModel) {
        deviceModel.setProjectId(UserUtils.getProjectId());
        deviceModel.setCreateBy(UserUtils.getUserId());
        deviceModel.setCreateDeptId(UserUtils.getDeptId());
        checkCodeUnique(deviceModel.getCode(), deviceModel.getProjectId(), null);
        return this.save(deviceModel);
    }

    @Override
    @Transactional
    public boolean updateDeviceModel(DeviceModel deviceModel) {
        checkCodeUnique(deviceModel.getCode(), deviceModel.getProjectId(), deviceModel.getId());
        deviceModel.setUpdateBy(UserUtils.getUserId());
        return this.updateById(deviceModel);
    }

    @Override
    @Transactional
    public boolean deleteDeviceModel(Long id) {
        if (hasRelatedDevices(id)) {
            throw new BusinessException("该型号存在关联的设备，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    public boolean hasRelatedDevices(Long id) {
        return false;
    }

    private void checkCodeUnique(String code, Long projectId, Long excludeId) {
        LambdaQueryWrapper<DeviceModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceModel::getCode, code)
               .eq(DeviceModel::getProjectId, projectId);
        if (excludeId != null) {
            wrapper.ne(DeviceModel::getId, excludeId);
        }
        if (this.count(wrapper) > 0) {
            throw new BusinessException("设备型号编码已存在");
        }
    }
}
