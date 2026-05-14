package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.controller.ProjectQuery;
import com.operation.module.entity.Project;
import com.operation.module.mapper.ProjectMapper;
import com.operation.module.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectMapper projectMapper;

    @Override
    public IPage<Project> pageList(ProjectQuery query) {
        Page<Project> page = new Page<>(query.getPageNum(), query.getPageSize());
        return projectMapper.selectPageList(page, query);
    }

    @Override
    public Project getById(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public boolean save(Project project) {
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        project.setDeleted(0);
        return projectMapper.insert(project) > 0;
    }

    @Override
    public boolean update(Project project) {
        project.setUpdateTime(LocalDateTime.now());
        return projectMapper.updateById(project) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Project project = new Project();
        project.setId(id);
        project.setDeleted(1);
        project.setUpdateTime(LocalDateTime.now());
        return projectMapper.updateById(project) > 0;
    }

    @Override
    public boolean updateStatus(Long id, String status) {
        Project project = new Project();
        project.setId(id);
        project.setStatus(status);
        project.setUpdateTime(LocalDateTime.now());
        return projectMapper.updateById(project) > 0;
    }

    @Override
    public boolean updateStage(Long id, String stage) {
        Project project = new Project();
        project.setId(id);
        project.setStage(stage);
        project.setUpdateTime(LocalDateTime.now());
        return projectMapper.updateById(project) > 0;
    }

    @Override
    public Map<String, Object> getStats(Long id) {
        Map<String, Object> stats = new HashMap<>();
        Project project = projectMapper.selectById(id);
        if (project != null) {
            stats.put("projectId", id);
            stats.put("projectName", project.getName());
            stats.put("status", project.getStatus());
            stats.put("stage", project.getStage());
            stats.put("memberCount", projectMapper.selectMemberCount(id));
            stats.put("deviceCount", projectMapper.selectDeviceCount(id));
        }
        return stats;
    }
}
