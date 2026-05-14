package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionTask;
import com.operation.module.entity.InspectionRecord;
import java.util.List;
import java.util.Map;

public interface IInspectionTaskService {

    IPage<InspectionTask> getTaskPage(Page<InspectionTask> page, Long projectId, String taskName, String taskStatus, Long assignedTo);

    List<InspectionTask> getTaskList(Long projectId, String taskStatus);

    InspectionTask getTaskById(Long id);

    boolean executeTask(Long id, InspectionRecord record);

    IPage<InspectionRecord> getRecordPage(Page<InspectionRecord> page, Long projectId, Long deviceId, Long inspectorId, String inspectionResult, String startTime, String endTime);

    InspectionRecord getRecordById(Long id);

    Map<String, Object> getInspectionStats(Long projectId, String startTime, String endTime);
}
