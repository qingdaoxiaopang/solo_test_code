package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.operation.module.entity.ProjectStage;
import com.operation.module.mapper.ProjectStageMapper;
import com.operation.module.service.IProjectStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStageServiceImpl implements IProjectStageService {

    private final ProjectStageMapper projectStageMapper;

    @Override
    public List<ProjectStage> listByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectStage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectStage::getProjectId, projectId);
        wrapper.eq(ProjectStage::getDeleted, 0);
        wrapper.orderByAsc(ProjectStage::getSort);
        return projectStageMapper.selectList(wrapper);
    }

    @Override
    public ProjectStage getById(Long id) {
        return projectStageMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ProjectStage stage) {
        stage.setCreateTime(LocalDateTime.now());
        stage.setUpdateTime(LocalDateTime.now());
        stage.setDeleted(0);
        return projectStageMapper.insert(stage) > 0;
    }

    @Override
    public boolean update(ProjectStage stage) {
        stage.setUpdateTime(LocalDateTime.now());
        return projectStageMapper.updateById(stage) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        LambdaUpdateWrapper<ProjectStage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ProjectStage::getId, id);
        wrapper.set(ProjectStage::getDeleted, 1);
        wrapper.set(ProjectStage::getUpdateTime, LocalDateTime.now());
        return projectStageMapper.update(null, wrapper) > 0;
    }
}
