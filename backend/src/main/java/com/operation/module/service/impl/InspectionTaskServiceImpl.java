package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionTask;
import com.operation.module.entity.InspectionRecord;
import com.operation.module.mapper.InspectionTaskMapper;
import com.operation.module.mapper.InspectionRecordMapper;
import com.operation.module.service.IInspectionTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InspectionTaskServiceImpl implements IInspectionTaskService {

    @Autowired
    private InspectionTaskMapper inspectionTaskMapper;

    @Autowired
    private InspectionRecordMapper inspectionRecordMapper;

    @Override
    public IPage<InspectionTask> getTaskPage(Page<InspectionTask> page, Long projectId, String taskName, String taskStatus, Long assignedTo) {
        return inspectionTaskMapper.selectTaskPage(page, projectId, taskName, taskStatus, assignedTo);
    }

    @Override
    public List<InspectionTask> getTaskList(Long projectId, String taskStatus) {
        return inspectionTaskMapper.selectTaskList(projectId, taskStatus);
    }

    @Override
    public InspectionTask getTaskById(Long id) {
        return inspectionTaskMapper.selectTaskById(id);
    }

    @Override
    @Transactional
    public boolean executeTask(Long id, InspectionRecord record) {
        InspectionTask task = inspectionTaskMapper.selectTaskById(id);
        if (task == null) {
            return false;
        }
        record.setTaskId(id);
        record.setPlanId(task.getPlanId());
        record.setInspectionTime(LocalDateTime.now());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        
        boolean result = inspectionRecordMapper.insertRecord(record) > 0;
        
        if (result && "NORMAL".equals(record.getInspectionResult())) {
            task.setActualEndTime(LocalDateTime.now());
            task.setTaskStatus("COMPLETED");
            inspectionTaskMapper.updateTask(task);
        }
        
        return result;
    }

    @Override
    public IPage<InspectionRecord> getRecordPage(Page<InspectionRecord> page, Long projectId, Long deviceId, Long inspectorId, String inspectionResult, String startTime, String endTime) {
        return inspectionRecordMapper.selectRecordPage(page, projectId, deviceId, inspectorId, inspectionResult, startTime, endTime);
    }

    @Override
    public InspectionRecord getRecordById(Long id) {
        return inspectionRecordMapper.selectRecordById(id);
    }

    @Override
    public Map<String, Object> getInspectionStats(Long projectId, String startTime, String endTime) {
        List<Map<String, Object>> stats = inspectionRecordMapper.selectInspectionStats(projectId, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("stats", stats);
        result.put("totalCount", stats.size());
        return result;
    }
}
