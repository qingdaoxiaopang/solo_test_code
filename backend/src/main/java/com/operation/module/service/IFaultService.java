package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.FaultRecord;
import java.util.List;
import java.util.Map;

public interface IFaultService {

    IPage<FaultRecord> getFaultPage(Page<FaultRecord> page, Long projectId, String faultNo, String faultType, String faultLevel, String faultStatus, Long deviceId);

    List<FaultRecord> getFaultList(Long projectId, String faultStatus);

    FaultRecord getFaultById(Long id);

    FaultRecord getFaultByNo(String faultNo);

    boolean reportFault(FaultRecord fault);

    boolean updateFault(FaultRecord fault);

    boolean handleFault(Long id, Long handlerId, String handlerName, String handleResult, String handlePhotos);

    boolean resolveFault(Long id, Long resolverId, String resolverName, String resolution, String resolutionPhotos);

    boolean closeFault(Long id, Long closerId, String closerName, String closeRemark);

    Map<String, Object> getFaultStats(Long projectId, String startTime, String endTime);

    List<FaultRecord> getFaultKnowledge(String faultType, String deviceType);

    boolean uploadFaultPhoto(Long id, String photoUrl);
}
