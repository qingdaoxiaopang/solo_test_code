package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.Device;
import com.operation.module.entity.DeviceAttachment;
import com.operation.module.entity.DeviceLog;
import com.operation.module.entity.DeviceParam;
import com.operation.module.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    @GetMapping
    public Result<IPage<Device>> getDevicePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String deviceName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String status) {
        Page<Device> page = new Page<>(pageNum, pageSize);
        IPage<Device> result = deviceService.getDevicePage(page, projectId, deviceName, deviceCode, deviceType, status);
        return Result.success(result);
    }

    @PostMapping
    public Result<Boolean> createDevice(@RequestBody Device device) {
        boolean result = deviceService.createDevice(device);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        return Result.success(device);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateDevice(@PathVariable Long id, @RequestBody Device device) {
        device.setId(id);
        boolean result = deviceService.updateDevice(device);
        return Result.success(result);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDevice(@PathVariable Long id) {
        boolean result = deviceService.deleteDevice(id);
        return Result.success(result);
    }

    @GetMapping("/export")
    public void exportDevices(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String deviceName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String status,
            HttpServletResponse response) {
        List<Device> devices = deviceService.exportDevices(projectId, deviceName, deviceCode, deviceType, status);
        Result<List<Device>> result = Result.success(devices);
    }

    @PostMapping("/import")
    public Result<Map<String, Object>> importDevices(@RequestBody List<Device> devices) {
        Map<String, Object> result = deviceService.importDevices(devices);
        return Result.success(result);
    }

    @GetMapping("/{id}/lifecycle")
    public Result<List<DeviceLog>> getDeviceLifecycle(@PathVariable Long id) {
        List<DeviceLog> lifecycle = deviceService.getDeviceLifecycle(id);
        return Result.success(lifecycle);
    }

    @PutMapping("/{id}/status")
    public Result<Boolean> updateDeviceStatus(@PathVariable Long id, @RequestParam String status) {
        boolean result = deviceService.updateDeviceStatus(id, status);
        return Result.success(result);
    }

    @PostMapping("/{id}/photos")
    public Result<Boolean> uploadDevicePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String photoUrl = uploadToMinIO(file, "device/photos");
        boolean result = deviceService.uploadDevicePhoto(id, photoUrl);
        return Result.success(result);
    }

    @PostMapping("/{id}/site-photos")
    public Result<Boolean> uploadSitePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String photoUrl = uploadToMinIO(file, "device/site-photos");
        boolean result = deviceService.uploadSitePhoto(id, photoUrl);
        return Result.success(result);
    }

    @GetMapping("/{id}/params")
    public Result<List<DeviceParam>> getDeviceParams(@PathVariable Long id) {
        List<DeviceParam> params = deviceService.getDeviceParams(id);
        return Result.success(params);
    }

    @PutMapping("/{id}/params")
    public Result<Boolean> updateDeviceParams(@PathVariable Long id, @RequestBody List<DeviceParam> params) {
        boolean result = deviceService.updateDeviceParams(id, params);
        return Result.success(result);
    }

    @GetMapping("/{id}/attachments")
    public Result<List<DeviceAttachment>> getDeviceAttachments(@PathVariable Long id) {
        List<DeviceAttachment> attachments = deviceService.getDeviceAttachments(id);
        return Result.success(attachments);
    }

    @PostMapping("/{id}/attachments")
    public Result<DeviceAttachment> uploadAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = uploadToMinIO(file, "device/attachments");
        Long fileSize = file.getSize();
        String fileType = file.getContentType();
        DeviceAttachment attachment = deviceService.uploadAttachment(id, fileName, filePath, fileSize, fileType);
        return Result.success(attachment);
    }

    @DeleteMapping("/{id}/attachments/{attachmentId}")
    public Result<Boolean> deleteAttachment(@PathVariable Long id, @PathVariable Long attachmentId) {
        boolean result = deviceService.deleteAttachment(id, attachmentId);
        return Result.success(result);
    }

    private String uploadToMinIO(MultipartFile file, String bucket) {
        return "https://minio.example.com/" + bucket + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
    }
}
