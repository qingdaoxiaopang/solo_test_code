package com.operation.module.service;

import java.util.List;
import java.util.Map;

public interface IDeviceStatsService {

    Map<String, Object> getOverviewStats(Long projectId);

    Map<String, Object> getStatusStats(Long projectId);

    List<Map<String, Object>> getDistributionStats(Long projectId, String groupBy);

    List<Map<String, Object>> getTrendStats(Long projectId, String period, String startTime, String endTime);

    List<Map<String, Object>> getDeviceMapData(Long projectId);
}
