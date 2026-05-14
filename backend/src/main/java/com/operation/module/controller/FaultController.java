package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.FaultRecord;
import com.operation.module.service.IFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/faults")
public class FaultController {

    @Autowired
    private IFaultService faultService;

    @GetMapping
    public Result<IPage<FaultRecord>> getFaultPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String faultNo,
            @RequestParam(required = false) String faultType,
            @RequestParam(required = false) String faultLevel,
            @RequestParam(required = false) String faultStatus,
            @RequestParam(required = false) Long deviceId) {
        Page<FaultRecord> page = new Page<>(pageNum, pageSize);
        IPage<FaultRecord> result = faultService.getFaultPage(page, projectId, faultNo, faultType, faultLevel, faultStatus, deviceId);
        return Result.success(result);
    }

    @PostMapping
    public Result<Boolean> reportFault(@RequestBody FaultRecord fault) {
        boolean result = faultService.reportFault(fault);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<FaultRecord> getFaultById(@PathVariable Long id) {
        FaultRecord fault = faultService.getFaultById(id);
        return Result.success(fault);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateFault(@PathVariable Long id, @RequestBody FaultRecord fault) {
        fault.setId(id);
        boolean result = faultService.updateFault(fault);
        return Result.success(result);
    }

    @PutMapping("/{id}/handle")
    public Result<Boolean> handleFault(
            @PathVariable Long id,
            @RequestParam Long handlerId,
            @RequestParam String handlerName,
            @RequestParam(required = false) String handleResult,
            @RequestParam(required = false) String handlePhotos) {
        boolean result = faultService.handleFault(id, handlerId, handlerName, handleResult, handlePhotos);
        return Result.success(result);
    }

    @PutMapping("/{id}/resolve")
    public Result<Boolean> resolveFault(
            @PathVariable Long id,
            @RequestParam Long resolverId,
            @RequestParam String resolverName,
            @RequestParam(required = false) String resolution,
            @RequestParam(required = false) String resolutionPhotos) {
        boolean result = faultService.resolveFault(id, resolverId, resolverName, resolution, resolutionPhotos);
        return Result.success(result);
    }

    @PutMapping("/{id}/close")
    public Result<Boolean> closeFault(
            @PathVariable Long id,
            @RequestParam Long closerId,
            @RequestParam String closerName,
            @RequestParam(required = false) String closeRemark) {
        boolean result = faultService.closeFault(id, closerId, closerName, closeRemark);
        return Result.success(result);
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getFaultStats(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Map<String, Object> stats = faultService.getFaultStats(projectId, startTime, endTime);
        return Result.success(stats);
    }

    @GetMapping("/knowledge")
    public Result<List<FaultRecord>> getFaultKnowledge(
            @RequestParam(required = false) String faultType,
            @RequestParam(required = false) String deviceType) {
        List<FaultRecord> knowledge = faultService.getFaultKnowledge(faultType, deviceType);
        return Result.success(knowledge);
    }

    @PostMapping("/{id}/photos")
    public Result<Boolean> uploadFaultPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String photoUrl = uploadToMinIO(file, "fault/photos");
        boolean result = faultService.uploadFaultPhoto(id, photoUrl);
        return Result.success(result);
    }

    private String uploadToMinIO(MultipartFile file, String bucket) {
        return "https://minio.example.com/" + bucket + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
    }
}
