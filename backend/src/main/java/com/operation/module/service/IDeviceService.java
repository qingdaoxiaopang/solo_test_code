package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.Device;
import com.operation.module.entity.DeviceAttachment;
import com.operation.module.entity.DeviceLog;
import com.operation.module.entity.DeviceParam;
import java.util.List;
import java.util.Map;

public interface IDeviceService {

    IPage<Device> getDevicePage(Page<Device> page, Long projectId, String deviceName, String deviceCode, String deviceType, String status);

    List<Device> getDeviceList(Long projectId, String deviceName, String deviceCode, String deviceType, String status);

    Device getDeviceById(Long id);

    boolean createDevice(Device device);

    boolean updateDevice(Device device);

    boolean deleteDevice(Long id);

    List<Device> exportDevices(Long projectId, String deviceName, String deviceCode, String deviceType, String status);

    Map<String, Object> importDevices(List<Device> devices);

    List<DeviceLog> getDeviceLifecycle(Long deviceId);

    boolean updateDeviceStatus(Long id, String status);

    boolean uploadDevicePhoto(Long id, String photoUrl);

    boolean uploadSitePhoto(Long id, String photoUrl);

    List<DeviceParam> getDeviceParams(Long deviceId);

    boolean updateDeviceParams(Long deviceId, List<DeviceParam> params);

    List<DeviceAttachment> getDeviceAttachments(Long deviceId);

    DeviceAttachment uploadAttachment(Long deviceId, String fileName, String filePath, Long fileSize, String fileType);

    boolean deleteAttachment(Long deviceId, Long attachmentId);
}
