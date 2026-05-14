package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.entity.Manufacturer;
import com.operation.module.config.mapper.DeviceModelMapper;
import com.operation.module.config.mapper.ManufacturerMapper;
import com.operation.module.config.service.IManufacturerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManufacturerServiceImpl extends ServiceImpl<ManufacturerMapper, Manufacturer> implements IManufacturerService {

    private final DeviceModelMapper deviceModelMapper;

    public ManufacturerServiceImpl(DeviceModelMapper deviceModelMapper) {
        this.deviceModelMapper = deviceModelMapper;
    }

    @Override
    @Transactional
    public boolean saveManufacturer(Manufacturer manufacturer) {
        manufacturer.setProjectId(UserUtils.getProjectId());
        manufacturer.setCreateBy(UserUtils.getUserId());
        manufacturer.setCreateDeptId(UserUtils.getDeptId());
        checkCodeUnique(manufacturer.getCode(), manufacturer.getProjectId(), null);
        return this.save(manufacturer);
    }

    @Override
    @Transactional
    public boolean updateManufacturer(Manufacturer manufacturer) {
        checkCodeUnique(manufacturer.getCode(), manufacturer.getProjectId(), manufacturer.getId());
        manufacturer.setUpdateBy(UserUtils.getUserId());
        return this.updateById(manufacturer);
    }

    @Override
    @Transactional
    public boolean deleteManufacturer(Long id) {
        if (hasRelatedModels(id)) {
            throw new BusinessException("该厂商存在关联的设备型号，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    public boolean hasRelatedModels(Long id) {
        Manufacturer manufacturer = this.getById(id);
        if (manufacturer == null) {
            return false;
        }
        LambdaQueryWrapper<DeviceModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceModel::getManufacturerCode, manufacturer.getCode())
               .eq(DeviceModel::getProjectId, manufacturer.getProjectId());
        return deviceModelMapper.selectCount(wrapper) > 0;
    }

    private void checkCodeUnique(String code, Long projectId, Long excludeId) {
        LambdaQueryWrapper<Manufacturer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Manufacturer::getCode, code)
               .eq(Manufacturer::getProjectId, projectId);
        if (excludeId != null) {
            wrapper.ne(Manufacturer::getId, excludeId);
        }
        if (this.count(wrapper) > 0) {
            throw new BusinessException("厂商编码已存在");
        }
    }
}
