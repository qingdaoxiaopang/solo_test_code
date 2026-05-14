package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.FaultRecord;
import com.operation.module.mapper.FaultRecordMapper;
import com.operation.module.service.IFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FaultServiceImpl implements IFaultService {

    @Autowired
    private FaultRecordMapper faultRecordMapper;

    @Override
    public IPage<FaultRecord> getFaultPage(Page<FaultRecord> page, Long projectId, String faultNo, String faultType, String faultLevel, String faultStatus, Long deviceId) {
        return faultRecordMapper.selectFaultPage(page, projectId, faultNo, faultType, faultLevel, faultStatus, deviceId);
    }

    @Override
    public List<FaultRecord> getFaultList(Long projectId, String faultStatus) {
        return faultRecordMapper.selectFaultList(projectId, faultStatus);
    }

    @Override
    public FaultRecord getFaultById(Long id) {
        return faultRecordMapper.selectFaultById(id);
    }

    @Override
    public FaultRecord getFaultByNo(String faultNo) {
        return faultRecordMapper.selectFaultByNo(faultNo);
    }

    @Override
    @Transactional
    public boolean reportFault(FaultRecord fault) {
        fault.setFaultNo(generateFaultNo());
        fault.setFaultStatus("REPORTED");
        fault.setReportTime(LocalDateTime.now());
        fault.setCreateTime(LocalDateTime.now());
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.insertFault(fault) > 0;
    }

    @Override
    @Transactional
    public boolean updateFault(FaultRecord fault) {
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.updateFault(fault) > 0;
    }

    @Override
    @Transactional
    public boolean handleFault(Long id, Long handlerId, String handlerName, String handleResult, String handlePhotos) {
        FaultRecord fault = faultRecordMapper.selectFaultById(id);
        if (fault == null) {
            return false;
        }
        fault.setHandlerId(handlerId);
        fault.setHandlerName(handlerName);
        fault.setHandleTime(LocalDateTime.now());
        fault.setHandleResult(handleResult);
        fault.setHandlePhotos(handlePhotos);
        fault.setFaultStatus("HANDLING");
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.updateFault(fault) > 0;
    }

    @Override
    @Transactional
    public boolean resolveFault(Long id, Long resolverId, String resolverName, String resolution, String resolutionPhotos) {
        FaultRecord fault = faultRecordMapper.selectFaultById(id);
        if (fault == null) {
            return false;
        }
        fault.setResolverId(resolverId);
        fault.setResolverName(resolverName);
        fault.setResolveTime(LocalDateTime.now());
        fault.setResolution(resolution);
        fault.setResolutionPhotos(resolutionPhotos);
        fault.setFaultStatus("RESOLVED");
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.updateFault(fault) > 0;
    }

    @Override
    @Transactional
    public boolean closeFault(Long id, Long closerId, String closerName, String closeRemark) {
        FaultRecord fault = faultRecordMapper.selectFaultById(id);
        if (fault == null) {
            return false;
        }
        fault.setCloserId(closerId);
        fault.setCloserName(closerName);
        fault.setCloseTime(LocalDateTime.now());
        fault.setCloseRemark(closeRemark);
        fault.setFaultStatus("CLOSED");
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.updateFault(fault) > 0;
    }

    @Override
    public Map<String, Object> getFaultStats(Long projectId, String startTime, String endTime) {
        List<Map<String, Object>> stats = faultRecordMapper.selectFaultStats(projectId, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("stats", stats);
        result.put("totalCount", stats.size());
        return result;
    }

    @Override
    public List<FaultRecord> getFaultKnowledge(String faultType, String deviceType) {
        return faultRecordMapper.selectFaultKnowledge(faultType, deviceType);
    }

    @Override
    @Transactional
    public boolean uploadFaultPhoto(Long id, String photoUrl) {
        FaultRecord fault = faultRecordMapper.selectFaultById(id);
        if (fault == null) {
            return false;
        }
        String photos = fault.getFaultPhotos();
        if (photos != null && !photos.isEmpty()) {
            fault.setFaultPhotos(photos + "," + photoUrl);
        } else {
            fault.setFaultPhotos(photoUrl);
        }
        fault.setUpdateTime(LocalDateTime.now());
        return faultRecordMapper.updateFault(fault) > 0;
    }

    private String generateFaultNo() {
        return "F" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}
