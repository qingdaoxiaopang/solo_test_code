package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface InspectionTaskMapper extends BaseMapper<InspectionTask> {

    IPage<InspectionTask> selectTaskPage(Page<InspectionTask> page,
                                        @Param("projectId") Long projectId,
                                        @Param("taskName") String taskName,
                                        @Param("taskStatus") String taskStatus,
                                        @Param("assignedTo") Long assignedTo);

    List<InspectionTask> selectTaskList(@Param("projectId") Long projectId,
                                        @Param("taskStatus") String taskStatus);

    InspectionTask selectTaskById(@Param("id") Long id);

    int insertTask(InspectionTask task);

    int updateTask(InspectionTask task);

    int deleteTask(@Param("id") Long id);

    int batchInsertTask(@Param("list") List<InspectionTask> list);
}
