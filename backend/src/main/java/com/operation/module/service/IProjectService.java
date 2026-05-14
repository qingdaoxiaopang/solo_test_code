package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.operation.module.controller.ProjectQuery;
import com.operation.module.entity.Project;

import java.util.Map;

public interface IProjectService {

    IPage<Project> pageList(ProjectQuery query);

    Project getById(Long id);

    boolean save(Project project);

    boolean update(Project project);

    boolean deleteById(Long id);

    boolean updateStatus(Long id, String status);

    boolean updateStage(Long id, String stage);

    Map<String, Object> getStats(Long id);
}
