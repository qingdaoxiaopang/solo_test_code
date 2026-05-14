package com.operation.module.controller;

import com.operation.module.service.IDeviceStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices/stats")
public class DeviceStatsController {

    @Autowired
    private IDeviceStatsService deviceStatsService;

    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverviewStats(@RequestParam(required = false) Long projectId) {
        Map<String, Object> stats = deviceStatsService.getOverviewStats(projectId);
        return Result.success(stats);
    }

    @GetMapping("/status")
    public Result<Map<String, Object>> getStatusStats(@RequestParam(required = false) Long projectId) {
        Map<String, Object> stats = deviceStatsService.getStatusStats(projectId);
        return Result.success(stats);
    }

    @GetMapping("/distribution")
    public Result<List<Map<String, Object>>> getDistributionStats(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false, defaultValue = "deviceType") String groupBy) {
        List<Map<String, Object>> distribution = deviceStatsService.getDistributionStats(projectId, groupBy);
        return Result.success(distribution);
    }

    @GetMapping("/trend")
    public Result<List<Map<String, Object>>> getTrendStats(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false, defaultValue = "month") String period,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Map<String, Object>> trend = deviceStatsService.getTrendStats(projectId, period, startTime, endTime);
        return Result.success(trend);
    }
}

@RestController
@RequestMapping("/api/devices")
class DeviceMapController {

    @Autowired
    private IDeviceStatsService deviceStatsService;

    @GetMapping("/map")
    public Result<List<Map<String, Object>>> getDeviceMapData(@RequestParam(required = false) Long projectId) {
        List<Map<String, Object>> mapData = deviceStatsService.getDeviceMapData(projectId);
        return Result.success(mapData);
    }
}
