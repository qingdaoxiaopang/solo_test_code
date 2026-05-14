package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.module.entity.Device;
import com.operation.module.entity.DeviceAttachment;
import com.operation.module.entity.DeviceLog;
import com.operation.module.entity.DeviceParam;
import com.operation.module.mapper.DeviceAttachmentMapper;
import com.operation.module.mapper.DeviceLogMapper;
import com.operation.module.mapper.DeviceMapper;
import com.operation.module.mapper.DeviceParamMapper;
import com.operation.module.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceParamMapper deviceParamMapper;

    @Autowired
    private DeviceAttachmentMapper deviceAttachmentMapper;

    @Autowired
    private DeviceLogMapper deviceLogMapper;

    @Override
    public IPage<Device> getDevicePage(Page<Device> page, Long projectId, String deviceName, String deviceCode, String deviceType, String status) {
        return deviceMapper.selectDevicePage(page, projectId, deviceName, deviceCode, deviceType, status);
    }

    @Override
    public List<Device> getDeviceList(Long projectId, String deviceName, String deviceCode, String deviceType, String status) {
        return deviceMapper.selectDeviceList(projectId, deviceName, deviceCode, deviceType, status);
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceMapper.selectDeviceById(id);
    }

    @Override
    @Transactional
    public boolean createDevice(Device device) {
        device.setCreateTime(LocalDateTime.now());
        device.setUpdateTime(LocalDateTime.now());
        device.setEnabledStatus("ENABLED");
        device.setStatus("NORMAL");
        return deviceMapper.insertDevice(device) > 0;
    }

    @Override
    @Transactional
    public boolean updateDevice(Device device) {
        device.setUpdateTime(LocalDateTime.now());
        return deviceMapper.updateDevice(device) > 0;
    }

    @Override
    @Transactional
    public boolean deleteDevice(Long id) {
        Device device = new Device();
        device.setId(id);
        device.setDelFlag(1);
        return deviceMapper.updateDevice(device) > 0;
    }

    @Override
    public List<Device> exportDevices(Long projectId, String deviceName, String deviceCode, String deviceType, String status) {
        return deviceMapper.selectDeviceList(projectId, deviceName, deviceCode, deviceType, status);
    }

    @Override
    @Transactional
    public Map<String, Object> importDevices(List<Device> devices) {
        int successCount = 0;
        int failCount = 0;
        for (Device device : devices) {
            device.setCreateTime(LocalDateTime.now());
            device.setUpdateTime(LocalDateTime.now());
            if (deviceMapper.insertDevice(device) > 0) {
                successCount++;
            } else {
                failCount++;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("totalCount", devices.size());
        return result;
    }

    @Override
    public List<DeviceLog> getDeviceLifecycle(Long deviceId) {
        return deviceLogMapper.selectLogsByDeviceId(deviceId);
    }

    @Override
    @Transactional
    public boolean updateDeviceStatus(Long id, String status) {
        Device device = new Device();
        device.setId(id);
        device.setStatus(status);
        device.setUpdateTime(LocalDateTime.now());
        return deviceMapper.updateDevice(device) > 0;
    }

    @Override
    @Transactional
    public boolean uploadDevicePhoto(Long id, String photoUrl) {
        Device device = new Device();
        device.setId(id);
        device.setMainPhoto(photoUrl);
        device.setUpdateTime(LocalDateTime.now());
        return deviceMapper.updateDevice(device) > 0;
    }

    @Override
    @Transactional
    public boolean uploadSitePhoto(Long id, String photoUrl) {
        Device device = deviceMapper.selectDeviceById(id);
        String existingPhotos = device.getSitePhotos();
        if (existingPhotos != null && !existingPhotos.isEmpty()) {
            device.setSitePhotos(existingPhotos + "," + photoUrl);
        } else {
            device.setSitePhotos(photoUrl);
        }
        device.setUpdateTime(LocalDateTime.now());
        return deviceMapper.updateDevice(device) > 0;
    }

    @Override
    public List<DeviceParam> getDeviceParams(Long deviceId) {
        return deviceParamMapper.selectParamsByDeviceId(deviceId);
    }

    @Override
    @Transactional
    public boolean updateDeviceParams(Long deviceId, List<DeviceParam> params) {
        deviceParamMapper.deleteParamsByDeviceId(deviceId);
        for (DeviceParam param : params) {
            param.setDeviceId(deviceId);
            param.setCreateTime(LocalDateTime.now());
            param.setUpdateTime(LocalDateTime.now());
            deviceParamMapper.insertDeviceParam(param);
        }
        return true;
    }

    @Override
    public List<DeviceAttachment> getDeviceAttachments(Long deviceId) {
        return deviceAttachmentMapper.selectAttachmentsByDeviceId(deviceId);
    }

    @Override
    @Transactional
    public DeviceAttachment uploadAttachment(Long deviceId, String fileName, String filePath, Long fileSize, String fileType) {
        DeviceAttachment attachment = new DeviceAttachment();
        attachment.setDeviceId(deviceId);
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachment.setFileSize(fileSize);
        attachment.setFileType(fileType);
        attachment.setUploadTime(LocalDateTime.now());
        deviceAttachmentMapper.insertAttachment(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public boolean deleteAttachment(Long deviceId, Long attachmentId) {
        return deviceAttachmentMapper.deleteAttachment(attachmentId) > 0;
    }
}
