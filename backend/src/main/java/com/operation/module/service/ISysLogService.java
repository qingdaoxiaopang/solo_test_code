package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.entity.SysLog;

import java.util.List;

public interface ISysLogService {
    
    IPage<SysLog> selectLogPage(Integer current, Integer size, String logType,
                                  String operationType, String username, String status,
                                  String startTime, String endTime);
    
    SysLog selectLogById(Long id);
    
    List<SysLog> selectLogList(SysLog log);
    
    boolean saveLog(SysLog log);
    
    boolean deleteLog(Long id);
    
    boolean deleteLogs(List<Long> ids);
    
    boolean clearLogs();
}
