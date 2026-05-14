package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.entity.SysDict;
import com.operation.module.entity.SysDictItem;

import java.util.List;

public interface ISysDictService {
    
    IPage<SysDict> selectDictPage(Integer current, Integer size, String dictName,
                                    String dictCode, String status);
    
    SysDict selectDictById(Long id);
    
    SysDict selectDictByCode(String dictCode);
    
    boolean saveDict(SysDict dict);
    
    boolean updateDict(SysDict dict);
    
    boolean deleteDict(Long id);
    
    boolean updateStatus(Long id, String status);
    
    List<SysDictItem> selectDictItemsByDictId(Long dictId);
    
    List<SysDictItem> selectDictItemsByDictCode(String dictCode);
    
    IPage<SysDictItem> selectDictItemPage(Integer current, Integer size, Long dictId,
                                           String itemText, String status);
    
    SysDictItem selectDictItemById(Long id);
    
    boolean saveDictItem(SysDictItem dictItem);
    
    boolean updateDictItem(SysDictItem dictItem);
    
    boolean deleteDictItem(Long id);
}
