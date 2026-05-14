package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysLog;
import com.operation.module.mapper.SysLogMapper;
import com.operation.module.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements ISysLogService {
    
    @Autowired
    private SysLogMapper sysLogMapper;
    
    @Override
    public IPage<SysLog> selectLogPage(Integer current, Integer size, String logType,
                                        String operationType, String username, String status,
                                        String startTime, String endTime) {
        Page<SysLog> page = new Page<>(current, size);
        return sysLogMapper.selectLogPage(page, logType, operationType, username, status, startTime, endTime);
    }
    
    @Override
    public SysLog selectLogById(Long id) {
        return sysLogMapper.selectById(id);
    }
    
    @Override
    public List<SysLog> selectLogList(SysLog log) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        if (log.getLogType() != null) {
            wrapper.eq(SysLog::getLogType, log.getLogType());
        }
        if (log.getOperationType() != null) {
            wrapper.eq(SysLog::getOperationType, log.getOperationType());
        }
        if (log.getUsername() != null) {
            wrapper.like(SysLog::getUsername, log.getUsername());
        }
        if (log.getStatus() != null) {
            wrapper.eq(SysLog::getStatus, log.getStatus());
        }
        wrapper.orderByDesc(SysLog::getCreateTime);
        return sysLogMapper.selectList(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveLog(SysLog log) {
        return sysLogMapper.insert(log) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLog(Long id) {
        return sysLogMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLogs(List<Long> ids) {
        return sysLogMapper.deleteBatchIds(ids) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean clearLogs() {
        return sysLogMapper.delete(null) > 0;
    }
}
