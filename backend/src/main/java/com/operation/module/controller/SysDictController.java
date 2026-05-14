package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.entity.Result;
import com.operation.module.entity.SysDict;
import com.operation.module.entity.SysDictItem;
import com.operation.module.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dicts")
@SaCheckLogin
public class SysDictController {
    
    @Autowired
    private ISysDictService sysDictService;
    
    @GetMapping
    @SaCheckPermission("system:dict:list")
    public Result<IPage<SysDict>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) String dictCode,
            @RequestParam(required = false) String status) {
        IPage<SysDict> page = sysDictService.selectDictPage(current, size, dictName, dictCode, status);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    @SaCheckPermission("system:dict:list")
    public Result<SysDict> getById(@PathVariable Long id) {
        SysDict dict = sysDictService.selectDictById(id);
        return Result.success(dict);
    }
    
    @GetMapping("/code/{code}")
    @SaCheckPermission("system:dict:list")
    public Result<SysDict> getByCode(@PathVariable String code) {
        SysDict dict = sysDictService.selectDictByCode(code);
        return Result.success(dict);
    }
    
    @PostMapping
    @SaCheckPermission("system:dict:add")
    public Result<Void> save(@RequestBody SysDict dict) {
        sysDictService.saveDict(dict);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @SaCheckPermission("system:dict:edit")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysDict dict) {
        dict.setId(id);
        sysDictService.updateDict(dict);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:dict:del")
    public Result<Void> delete(@PathVariable Long id) {
        sysDictService.deleteDict(id);
        return Result.success();
    }
    
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:dict:edit")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        sysDictService.updateStatus(id, status);
        return Result.success();
    }
    
    @GetMapping("/{code}/items")
    @SaCheckPermission("system:dict:list")
    public Result<List<SysDictItem>> getItemsByCode(@PathVariable String code) {
        List<SysDictItem> items = sysDictService.selectDictItemsByDictCode(code);
        return Result.success(items);
    }
    
    @GetMapping("/{id}/items")
    @SaCheckPermission("system:dict:list")
    public Result<List<SysDictItem>> getItemsByDictId(@PathVariable Long id) {
        List<SysDictItem> items = sysDictService.selectDictItemsByDictId(id);
        return Result.success(items);
    }
    
    @GetMapping("/{dictId}/items/page")
    @SaCheckPermission("system:dict:list")
    public Result<IPage<SysDictItem>> getItemsPage(
            @PathVariable Long dictId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String itemText,
            @RequestParam(required = false) String status) {
        IPage<SysDictItem> page = sysDictService.selectDictItemPage(current, size, dictId, itemText, status);
        return Result.success(page);
    }
    
    @GetMapping("/items/{id}")
    @SaCheckPermission("system:dict:list")
    public Result<SysDictItem> getItemById(@PathVariable Long id) {
        SysDictItem item = sysDictService.selectDictItemById(id);
        return Result.success(item);
    }
    
    @PostMapping("/items")
    @SaCheckPermission("system:dict:add")
    public Result<Void> saveItem(@RequestBody SysDictItem dictItem) {
        sysDictService.saveDictItem(dictItem);
        return Result.success();
    }
    
    @PutMapping("/items/{id}")
    @SaCheckPermission("system:dict:edit")
    public Result<Void> updateItem(@PathVariable Long id, @RequestBody SysDictItem dictItem) {
        dictItem.setId(id);
        sysDictService.updateDictItem(dictItem);
        return Result.success();
    }
    
    @DeleteMapping("/items/{id}")
    @SaCheckPermission("system:dict:del")
    public Result<Void> deleteItem(@PathVariable Long id) {
        sysDictService.deleteDictItem(id);
        return Result.success();
    }
}
