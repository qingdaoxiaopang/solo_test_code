package com.operation.module.config.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.Manufacturer;
import com.operation.module.config.service.IManufacturerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/manufacturers")
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public Result<List<Manufacturer>> getManufacturerList() {
        Long projectId = UserUtils.getProjectId();
        LambdaQueryWrapper<Manufacturer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Manufacturer::getProjectId, projectId)
               .orderByDesc(Manufacturer::getCreateTime);
        List<Manufacturer> list = manufacturerService.list(wrapper);
        return Result.success(list);
    }

    @PostMapping
    public Result<Boolean> createManufacturer(@RequestBody Manufacturer manufacturer) {
        boolean success = manufacturerService.saveManufacturer(manufacturer);
        return Result.success(success);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateManufacturer(@PathVariable Long id, @RequestBody Manufacturer manufacturer) {
        manufacturer.setId(id);
        boolean success = manufacturerService.updateManufacturer(manufacturer);
        return Result.success(success);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteManufacturer(@PathVariable Long id) {
        boolean success = manufacturerService.deleteManufacturer(id);
        return Result.success(success);
    }
}
