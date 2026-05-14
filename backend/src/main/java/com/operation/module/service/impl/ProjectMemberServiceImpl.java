package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.module.entity.ProjectMember;
import com.operation.module.mapper.ProjectMemberMapper;
import com.operation.module.service.IProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements IProjectMemberService {

    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public List<ProjectMember> listByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectMember::getProjectId, projectId);
        wrapper.eq(ProjectMember::getDeleted, 0);
        return projectMemberMapper.selectList(wrapper);
    }

    @Override
    public ProjectMember getById(Long id) {
        return projectMemberMapper.selectById(id);
    }

    @Override
    public boolean save(ProjectMember member) {
        member.setCreateTime(LocalDateTime.now());
        member.setUpdateTime(LocalDateTime.now());
        member.setDeleted(0);
        return projectMemberMapper.insert(member) > 0;
    }

    @Override
    public boolean deleteByProjectIdAndUserId(Long projectId, Long userId) {
        ProjectMember member = new ProjectMember();
        member.setDeleted(1);
        member.setUpdateTime(LocalDateTime.now());
        return projectMemberMapper.deleteByProjectIdAndUserId(projectId, userId) > 0;
    }

    @Override
    public boolean updateRole(Long projectId, Long userId, String role) {
        ProjectMember existingMember = projectMemberMapper.selectByProjectIdAndUserId(projectId, userId);
        if (existingMember != null) {
            existingMember.setRole(role);
            existingMember.setUpdateTime(LocalDateTime.now());
            return projectMemberMapper.updateById(existingMember) > 0;
        }
        return false;
    }
}
