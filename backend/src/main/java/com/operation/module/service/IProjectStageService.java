package com.operation.module.service;

import com.operation.module.entity.ProjectStage;

import java.util.List;

public interface IProjectStageService {

    List<ProjectStage> listByProjectId(Long projectId);

    ProjectStage getById(Long id);

    boolean save(ProjectStage stage);

    boolean update(ProjectStage stage);

    boolean deleteById(Long id);
}
