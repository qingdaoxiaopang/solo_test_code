package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface InspectionPlanMapper extends BaseMapper<InspectionPlan> {

    IPage<InspectionPlan> selectPlanPage(Page<InspectionPlan> page,
                                         @Param("projectId") Long projectId,
                                         @Param("planName") String planName,
                                         @Param("enabledStatus") String enabledStatus,
                                         @Param("completedStatus") String completedStatus);

    List<InspectionPlan> selectPlanList(@Param("projectId") Long projectId,
                                         @Param("planName") String planName);

    InspectionPlan selectPlanById(@Param("id") Long id);

    int insertPlan(InspectionPlan plan);

    int updatePlan(InspectionPlan plan);

    int deletePlan(@Param("id") Long id);

    int batchInsertPlan(@Param("list") List<InspectionPlan> list);
}
