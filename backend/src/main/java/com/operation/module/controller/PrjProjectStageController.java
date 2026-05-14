package com.operation.module.controller;

import com.operation.common.core.domain.Result;
import com.operation.module.entity.ProjectStage;
import com.operation.module.service.IProjectStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/stages")
@RequiredArgsConstructor
public class PrjProjectStageController {

    private final IProjectStageService projectStageService;

    @GetMapping
    public Result<Object> list(@PathVariable Long projectId) {
        return Result.success(projectStageService.listByProjectId(projectId));
    }

    @PostMapping
    public Result<Boolean> add(@PathVariable Long projectId, @RequestBody ProjectStage stage) {
        stage.setProjectId(projectId);
        return Result.success(projectStageService.save(stage));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody ProjectStage stage) {
        stage.setId(id);
        return Result.success(projectStageService.update(stage));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(projectStageService.deleteById(id));
    }
}
