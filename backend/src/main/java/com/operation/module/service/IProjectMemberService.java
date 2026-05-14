package com.operation.module.service;

import com.operation.module.entity.ProjectMember;

import java.util.List;

public interface IProjectMemberService {

    List<ProjectMember> listByProjectId(Long projectId);

    ProjectMember getById(Long id);

    boolean save(ProjectMember member);

    boolean deleteByProjectIdAndUserId(Long projectId, Long userId);

    boolean updateRole(Long projectId, Long userId, String role);
}
