package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.entity.SysApiKey;

import java.util.List;

public interface ISysApiKeyService {
    
    IPage<SysApiKey> selectApiKeyPage(Integer current, Integer size, String keyName, String status);
    
    SysApiKey selectApiKeyById(Long id);
    
    List<SysApiKey> selectApiKeysByUserId(Long userId);
    
    boolean saveApiKey(SysApiKey apiKey);
    
    boolean updateApiKey(SysApiKey apiKey);
    
    boolean deleteApiKey(Long id);
    
    boolean updateStatus(Long id, String status);
    
    boolean resetSecret(Long id);
    
    boolean incrementRequestCount(Long id);
}
