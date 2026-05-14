package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.operation.module.entity.Device;
import com.operation.module.mapper.DeviceMapper;
import com.operation.module.service.IDeviceStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeviceStatsServiceImpl implements IDeviceStatsService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Map<String, Object> getOverviewStats(Long projectId) {
        Map<String, Object> stats = new HashMap<>();
        
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        if (projectId != null) {
            queryWrapper.eq("project_id", projectId);
        }
        queryWrapper.eq("del_flag", 0);
        
        long totalCount = deviceMapper.selectCount(queryWrapper);
        
        queryWrapper.eq("status", "NORMAL");
        long normalCount = deviceMapper.selectCount(queryWrapper);
        
        queryWrapper.eq("status", "FAULT");
        long faultCount = deviceMapper.selectCount(queryWrapper);
        
        queryWrapper.eq("status", "MAINTENANCE");
        long maintenanceCount = deviceMapper.selectCount(queryWrapper);
        
        stats.put("totalCount", totalCount);
        stats.put("normalCount", normalCount);
        stats.put("faultCount", faultCount);
        stats.put("maintenanceCount", maintenanceCount);
        stats.put("normalRate", totalCount > 0 ? (normalCount * 100.0 / totalCount) : 0);
        
        return stats;
    }

    @Override
    public Map<String, Object> getStatusStats(Long projectId) {
        Map<String, Object> stats = new HashMap<>();
        
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        if (projectId != null) {
            queryWrapper.eq("project_id", projectId);
        }
        queryWrapper.eq("del_flag", 0);
        queryWrapper.groupBy("status");
        queryWrapper.select("status", "count(*) as count");
        
        List<Map<String, Object>> statusList = new ArrayList<>();
        List<Device> devices = deviceMapper.selectList(queryWrapper);
        
        for (Device device : devices) {
            Map<String, Object> item = new HashMap<>();
            item.put("status", device.getStatus());
            item.put("count", 1);
            statusList.add(item);
        }
        
        stats.put("statusList", statusList);
        return stats;
    }

    @Override
    public List<Map<String, Object>> getDistributionStats(Long projectId, String groupBy) {
        List<Map<String, Object>> distribution = new ArrayList<>();
        
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        if (projectId != null) {
            queryWrapper.eq("project_id", projectId);
        }
        queryWrapper.eq("del_flag", 0);
        
        if ("deviceType".equals(groupBy)) {
            queryWrapper.select("device_type as name", "count(*) as value");
            queryWrapper.groupBy("device_type");
        } else if ("location".equals(groupBy)) {
            queryWrapper.select("installation_location as name", "count(*) as value");
            queryWrapper.groupBy("installation_location");
        } else if ("manufacturer".equals(groupBy)) {
            queryWrapper.select("manufacturer as name", "count(*) as value");
            queryWrapper.groupBy("manufacturer");
        } else {
            queryWrapper.select("status as name", "count(*) as value");
            queryWrapper.groupBy("status");
        }
        
        List<Device> devices = deviceMapper.selectList(queryWrapper);
        for (Device device : devices) {
            Map<String, Object> item = new HashMap<>();
            if ("deviceType".equals(groupBy)) {
                item.put("name", device.getDeviceType());
            } else if ("location".equals(groupBy)) {
                item.put("name", device.getInstallationLocation());
            } else if ("manufacturer".equals(groupBy)) {
                item.put("name", device.getManufacturer());
            } else {
                item.put("name", device.getStatus());
            }
            item.put("value", 1);
            distribution.add(item);
        }
        
        return distribution;
    }

    @Override
    public List<Map<String, Object>> getTrendStats(Long projectId, String period, String startTime, String endTime) {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        Map<String, Object> currentMonth = new HashMap<>();
        currentMonth.put("month", "2024-01");
        currentMonth.put("newDevice", 10);
        currentMonth.put("faultDevice", 2);
        currentMonth.put("maintenanceDevice", 5);
        trend.add(currentMonth);
        
        Map<String, Object> nextMonth = new HashMap<>();
        nextMonth.put("month", "2024-02");
        nextMonth.put("newDevice", 15);
        nextMonth.put("faultDevice", 3);
        nextMonth.put("maintenanceDevice", 8);
        trend.add(nextMonth);
        
        return trend;
    }

    @Override
    public List<Map<String, Object>> getDeviceMapData(Long projectId) {
        List<Map<String, Object>> mapData = new ArrayList<>();
        
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        if (projectId != null) {
            queryWrapper.eq("project_id", projectId);
        }
        queryWrapper.eq("del_flag", 0);
        queryWrapper.isNotNull("longitude");
        queryWrapper.isNotNull("latitude");
        queryWrapper.ne("longitude", 0);
        queryWrapper.ne("latitude", 0);
        
        List<Device> devices = deviceMapper.selectList(queryWrapper);
        for (Device device : devices) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", device.getId());
            item.put("name", device.getDeviceName());
            item.put("code", device.getDeviceCode());
            item.put("type", device.getDeviceType());
            item.put("status", device.getStatus());
            item.put("longitude", device.getLongitude());
            item.put("latitude", device.getLatitude());
            item.put("location", device.getInstallationLocation());
            mapData.add(item);
        }
        
        return mapData;
    }
}
