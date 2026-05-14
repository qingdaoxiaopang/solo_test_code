package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.DeviceType;
import com.operation.module.config.mapper.DeviceTypeMapper;
import com.operation.module.config.service.IDeviceTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements IDeviceTypeService {

    @Override
    public List<DeviceType> getDeviceTypeTree(Long projectId) {
        LambdaQueryWrapper<DeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceType::getProjectId, projectId)
               .orderByAsc(DeviceType::getSort)
               .orderByAsc(DeviceType::getCreateTime);
        List<DeviceType> allTypes = this.list(wrapper);
        return buildTree(allTypes, 0L);
    }

    private List<DeviceType> buildTree(List<DeviceType> allTypes, Long parentId) {
        return allTypes.stream()
                .filter(type -> (parentId == null && type.getParentId() == null) ||
                               (parentId != null && parentId.equals(type.getParentId())))
                .peek(type -> type.setChildren(buildTree(allTypes, type.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean saveDeviceType(DeviceType deviceType) {
        deviceType.setProjectId(UserUtils.getProjectId());
        deviceType.setCreateBy(UserUtils.getUserId());
        deviceType.setCreateDeptId(UserUtils.getDeptId());
        checkCodeUnique(deviceType.getCode(), deviceType.getProjectId(), null);
        return this.save(deviceType);
    }

    @Override
    @Transactional
    public boolean updateDeviceType(DeviceType deviceType) {
        checkCodeUnique(deviceType.getCode(), deviceType.getProjectId(), deviceType.getId());
        deviceType.setUpdateBy(UserUtils.getUserId());
        return this.updateById(deviceType);
    }

    @Override
    @Transactional
    public boolean deleteDeviceType(Long id) {
        if (hasChildren(id)) {
            throw new BusinessException("该设备类型存在子类型，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    public boolean hasChildren(Long id) {
        LambdaQueryWrapper<DeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceType::getParentId, id);
        return this.count(wrapper) > 0;
    }

    private void checkCodeUnique(String code, Long projectId, Long excludeId) {
        LambdaQueryWrapper<DeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceType::getCode, code)
               .eq(DeviceType::getProjectId, projectId);
        if (excludeId != null) {
            wrapper.ne(DeviceType::getId, excludeId);
        }
        if (this.count(wrapper) > 0) {
            throw new BusinessException("设备类型编码已存在");
        }
    }
}
