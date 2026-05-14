package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.ProjectMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    List<ProjectMember> selectByProjectId(@Param("projectId") Long projectId);

    ProjectMember selectByProjectIdAndUserId(@Param("projectId") Long projectId, @Param("userId") Long userId);

    int deleteByProjectIdAndUserId(@Param("projectId") Long projectId, @Param("userId") Long userId);
}
