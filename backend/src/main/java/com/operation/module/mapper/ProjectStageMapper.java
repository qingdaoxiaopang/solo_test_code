package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.ProjectStage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectStageMapper extends BaseMapper<ProjectStage> {

    List<ProjectStage> selectByProjectId(Long projectId);

    List<ProjectStage> selectByProjectIdOrderBySort(Long projectId);
}
