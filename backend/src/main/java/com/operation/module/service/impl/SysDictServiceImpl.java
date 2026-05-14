package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysDict;
import com.operation.module.entity.SysDictItem;
import com.operation.module.mapper.SysDictMapper;
import com.operation.module.mapper.SysDictItemMapper;
import com.operation.module.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDictServiceImpl implements ISysDictService {
    
    @Autowired
    private SysDictMapper sysDictMapper;
    
    @Autowired
    private SysDictItemMapper sysDictItemMapper;
    
    @Override
    public IPage<SysDict> selectDictPage(Integer current, Integer size, String dictName,
                                          String dictCode, String status) {
        Page<SysDict> page = new Page<>(current, size);
        return sysDictMapper.selectDictPage(page, dictName, dictCode, status);
    }
    
    @Override
    public SysDict selectDictById(Long id) {
        return sysDictMapper.selectById(id);
    }
    
    @Override
    public SysDict selectDictByCode(String dictCode) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictCode, dictCode);
        return sysDictMapper.selectOne(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDict(SysDict dict) {
        return sysDictMapper.insert(dict) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDict(SysDict dict) {
        return sysDictMapper.updateById(dict) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDict(Long id) {
        sysDictItemMapper.delete(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId, id));
        return sysDictMapper.deleteById(id) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, String status) {
        SysDict dict = new SysDict();
        dict.setId(id);
        dict.setStatus(status);
        return sysDictMapper.updateById(dict) > 0;
    }
    
    @Override
    public List<SysDictItem> selectDictItemsByDictId(Long dictId) {
        return sysDictItemMapper.selectDictItemsByDictId(dictId);
    }
    
    @Override
    public List<SysDictItem> selectDictItemsByDictCode(String dictCode) {
        return sysDictItemMapper.selectDictItemsByDictCode(dictCode);
    }
    
    @Override
    public IPage<SysDictItem> selectDictItemPage(Integer current, Integer size, Long dictId,
                                                  String itemText, String status) {
        Page<SysDictItem> page = new Page<>(current, size);
        return sysDictItemMapper.selectDictItemPage(page, dictId, itemText, status);
    }
    
    @Override
    public SysDictItem selectDictItemById(Long id) {
        return sysDictItemMapper.selectById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDictItem(SysDictItem dictItem) {
        return sysDictItemMapper.insert(dictItem) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictItem(SysDictItem dictItem) {
        return sysDictItemMapper.updateById(dictItem) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDictItem(Long id) {
        return sysDictItemMapper.deleteById(id) > 0;
    }
}
