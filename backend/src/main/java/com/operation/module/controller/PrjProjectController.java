package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.common.core.domain.PageResult;
import com.operation.common.core.domain.Result;
import com.operation.module.controller.ProjectQuery;
import com.operation.module.entity.Project;
import com.operation.module.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class PrjProjectController {

    private final IProjectService projectService;

    @GetMapping
    public Result<PageResult<Project>> list(ProjectQuery query) {
        IPage<Project> page = projectService.pageList(query);
        return Result.success(PageResult.of(
                page.getRecords(),
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        ));
    }

    @PostMapping
    public Result<Boolean> create(@RequestBody Project project) {
        return Result.success(projectService.save(project));
    }

    @GetMapping("/{id}")
    public Result<Project> detail(@PathVariable Long id) {
        Project project = projectService.getById(id);
        return Result.success(project);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        return Result.success(projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(projectService.deleteById(id));
    }

    @PutMapping("/{id}/status")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String status = params.get("status");
        return Result.success(projectService.updateStatus(id, status));
    }

    @PutMapping("/{id}/stage")
    public Result<Boolean> updateStage(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String stage = params.get("stage");
        return Result.success(projectService.updateStage(id, stage));
    }

    @GetMapping("/{id}/stats")
    public Result<Map<String, Object>> getStats(@PathVariable Long id) {
        return Result.success(projectService.getStats(id));
    }
}
