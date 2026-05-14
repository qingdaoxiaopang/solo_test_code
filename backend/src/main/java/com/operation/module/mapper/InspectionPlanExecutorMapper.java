package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.InspectionPlanExecutor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface InspectionPlanExecutorMapper extends BaseMapper<InspectionPlanExecutor> {

    List<InspectionPlanExecutor> selectExecutorsByPlanId(@Param("planId") Long planId);

    int insertPlanExecutor(InspectionPlanExecutor executor);

    int deletePlanExecutor(@Param("planId") Long planId, @Param("executorId") Long executorId);

    int deleteExecutorsByPlanId(@Param("planId") Long planId);

    int batchInsertPlanExecutor(@Param("list") List<InspectionPlanExecutor> list);

    int countByPlanId(@Param("planId") Long planId);
}
