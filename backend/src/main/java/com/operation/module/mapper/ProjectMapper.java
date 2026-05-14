package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.controller.ProjectQuery;
import com.operation.module.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    IPage<Project> selectPageList(Page<Project> page, @Param("query") ProjectQuery query);

    Long selectMemberCount(@Param("projectId") Long projectId);

    Long selectDeviceCount(@Param("projectId") Long projectId);
}
