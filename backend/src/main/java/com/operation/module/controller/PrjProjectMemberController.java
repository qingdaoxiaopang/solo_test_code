package com.operation.module.controller;

import com.operation.common.core.domain.Result;
import com.operation.module.entity.ProjectMember;
import com.operation.module.service.IProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class PrjProjectMemberController {

    private final IProjectMemberService projectMemberService;

    @GetMapping
    public Result<Object> list(@PathVariable Long projectId) {
        return Result.success(projectMemberService.listByProjectId(projectId));
    }

    @PostMapping
    public Result<Boolean> add(@PathVariable Long projectId, @RequestBody ProjectMember member) {
        member.setProjectId(projectId);
        return Result.success(projectMemberService.save(member));
    }

    @DeleteMapping("/{userId}")
    public Result<Boolean> remove(@PathVariable Long projectId, @PathVariable Long userId) {
        return Result.success(projectMemberService.deleteByProjectIdAndUserId(projectId, userId));
    }

    @PutMapping("/{userId}")
    public Result<Boolean> updateRole(@PathVariable Long projectId, @PathVariable Long userId, @RequestBody Map<String, String> params) {
        String role = params.get("role");
        return Result.success(projectMemberService.updateRole(projectId, userId, role));
    }
}
