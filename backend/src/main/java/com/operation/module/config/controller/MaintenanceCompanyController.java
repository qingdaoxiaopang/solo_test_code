package com.operation.module.config.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.CompanyModel;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.entity.MaintenanceCompany;
import com.operation.module.config.service.IMaintenanceCompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/maintenance-companies")
public class MaintenanceCompanyController {

    private final IMaintenanceCompanyService maintenanceCompanyService;

    public MaintenanceCompanyController(IMaintenanceCompanyService maintenanceCompanyService) {
        this.maintenanceCompanyService = maintenanceCompanyService;
    }

    @GetMapping
    public Result<List<MaintenanceCompany>> getMaintenanceCompanyList() {
        Long projectId = UserUtils.getProjectId();
        LambdaQueryWrapper<MaintenanceCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceCompany::getProjectId, projectId)
               .orderByDesc(MaintenanceCompany::getCreateTime);
        List<MaintenanceCompany> list = maintenanceCompanyService.list(wrapper);
        return Result.success(list);
    }

    @PostMapping
    public Result<Boolean> createMaintenanceCompany(@RequestBody MaintenanceCompany company) {
        boolean success = maintenanceCompanyService.saveMaintenanceCompany(company);
        return Result.success(success);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateMaintenanceCompany(@PathVariable Long id, @RequestBody MaintenanceCompany company) {
        company.setId(id);
        boolean success = maintenanceCompanyService.updateMaintenanceCompany(company);
        return Result.success(success);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteMaintenanceCompany(@PathVariable Long id) {
        boolean success = maintenanceCompanyService.deleteMaintenanceCompany(id);
        return Result.success(success);
    }

    @GetMapping("/{id}/models")
    public Result<List<DeviceModel>> getCompanyModels(@PathVariable Long id) {
        List<DeviceModel> models = maintenanceCompanyService.getCompanyModels(id);
        return Result.success(models);
    }

    @PostMapping("/{id}/models")
    public Result<Boolean> addCompanyModel(@PathVariable Long id, @RequestParam Long modelId) {
        boolean success = maintenanceCompanyService.addCompanyModel(id, modelId);
        return Result.success(success);
    }

    @DeleteMapping("/{id}/models/{modelId}")
    public Result<Boolean> removeCompanyModel(@PathVariable Long id, @PathVariable Long modelId) {
        boolean success = maintenanceCompanyService.removeCompanyModel(id, modelId);
        return Result.success(success);
    }
}
