package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysApiKey;
import com.operation.module.mapper.SysApiKeyMapper;
import com.operation.module.service.ISysApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SysApiKeyServiceImpl implements ISysApiKeyService {
    
    @Autowired
    private SysApiKeyMapper sysApiKeyMapper;
    
    @Override
    public IPage<SysApiKey> selectApiKeyPage(Integer current, Integer size, String keyName, String status) {
        Page<SysApiKey> page = new Page<>(current, size);
        return sysApiKeyMapper.selectApiKeyPage(page, keyName, status);
    }
    
    @Override
    public SysApiKey selectApiKeyById(Long id) {
        return sysApiKeyMapper.selectById(id);
    }
    
    @Override
    public List<SysApiKey> selectApiKeysByUserId(Long userId) {
        return sysApiKeyMapper.selectApiKeysByUserId(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveApiKey(SysApiKey apiKey) {
        apiKey.setApiKey(generateApiKey());
        apiKey.setSecretKey(generateSecretKey());
        apiKey.setTotalRequests(0L);
        apiKey.setTodayRequests(0L);
        return sysApiKeyMapper.insert(apiKey) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateApiKey(SysApiKey apiKey) {
        return sysApiKeyMapper.updateById(apiKey) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteApiKey(Long id) {
        return sysApiKeyMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String status) {
        SysApiKey apiKey = new SysApiKey();
        apiKey.setId(id);
        apiKey.setStatus(status);
        return sysApiKeyMapper.updateById(apiKey) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetSecret(Long id) {
        SysApiKey apiKey = new SysApiKey();
        apiKey.setId(id);
        apiKey.setSecretKey(generateSecretKey());
        return sysApiKeyMapper.updateById(apiKey) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrementRequestCount(Long id) {
        SysApiKey apiKey = sysApiKeyMapper.selectById(id);
        if (apiKey != null) {
            apiKey.setTotalRequests(apiKey.getTotalRequests() + 1);
            apiKey.setTodayRequests(apiKey.getTodayRequests() + 1);
            apiKey.setLastRequestTime(LocalDateTime.now());
            return sysApiKeyMapper.updateById(apiKey) > 0;
        }
        return false;
    }
    
    private String generateApiKey() {
        return "ak_" + UUID.randomUUID().toString().replace("-", "");
    }
    
    private String generateSecretKey() {
        return "sk_" + UUID.randomUUID().toString().replace("-", "");
    }
}
