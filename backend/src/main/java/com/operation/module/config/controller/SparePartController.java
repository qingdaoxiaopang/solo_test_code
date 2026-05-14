package com.operation.module.config.controller;

import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.SparePart;
import com.operation.module.config.entity.SparePartCategory;
import com.operation.module.config.entity.SparePartRecord;
import com.operation.module.config.service.ISparePartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class SparePartController {

    private final ISparePartService sparePartService;

    public SparePartController(ISparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @GetMapping("/spare-part-categories")
    public Result<List<SparePartCategory>> getSparePartCategoryTree() {
        Long projectId = UserUtils.getProjectId();
        List<SparePartCategory> tree = sparePartService.getSparePartCategoryTree(projectId);
        return Result.success(tree);
    }

    @PostMapping("/spare-part-categories")
    public Result<Boolean> createSparePartCategory(@RequestBody SparePartCategory category) {
        boolean success = sparePartService.saveSparePartCategory(category);
        return Result.success(success);
    }

    @PutMapping("/spare-part-categories/{id}")
    public Result<Boolean> updateSparePartCategory(@PathVariable Long id, @RequestBody SparePartCategory category) {
        category.setId(id);
        boolean success = sparePartService.updateSparePartCategory(category);
        return Result.success(success);
    }

    @DeleteMapping("/spare-part-categories/{id}")
    public Result<Boolean> deleteSparePartCategory(@PathVariable Long id) {
        boolean success = sparePartService.deleteSparePartCategory(id);
        return Result.success(success);
    }

    @GetMapping("/spare-parts")
    public Result<List<SparePart>> getSparePartList() {
        Long projectId = UserUtils.getProjectId();
        List<SparePart> list = sparePartService.getSparePartList(projectId);
        return Result.success(list);
    }

    @PostMapping("/spare-parts")
    public Result<Boolean> createSparePart(@RequestBody SparePart sparePart) {
        boolean success = sparePartService.saveSparePart(sparePart);
        return Result.success(success);
    }

    @PutMapping("/spare-parts/{id}")
    public Result<Boolean> updateSparePart(@PathVariable Long id, @RequestBody SparePart sparePart) {
        sparePart.setId(id);
        boolean success = sparePartService.updateSparePart(sparePart);
        return Result.success(success);
    }

    @DeleteMapping("/spare-parts/{id}")
    public Result<Boolean> deleteSparePart(@PathVariable Long id) {
        boolean success = sparePartService.deleteSparePart(id);
        return Result.success(success);
    }

    @GetMapping("/spare-parts/{id}/records")
    public Result<List<SparePartRecord>> getSparePartRecords(@PathVariable Long id) {
        List<SparePartRecord> records = sparePartService.getSparePartRecords(id);
        return Result.success(records);
    }

    @PostMapping("/spare-part-records")
    public Result<Boolean> applySparePart(@RequestBody SparePartRecord record) {
        boolean success = sparePartService.applySparePart(record);
        return Result.success(success);
    }

    @PutMapping("/spare-part-records/{id}/approve")
    public Result<Boolean> approveSparePartRecord(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long approverId = UserUtils.getUserId();
        String remark = (String) params.get("remark");
        boolean success = sparePartService.approveSparePartRecord(id, approverId, remark);
        return Result.success(success);
    }

    @PutMapping("/spare-part-records/{id}/complete")
    public Result<Boolean> completeSparePartRecord(@PathVariable Long id) {
        boolean success = sparePartService.completeSparePartRecord(id);
        return Result.success(success);
    }
}
