package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.InspectionPlanDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface InspectionPlanDeviceMapper extends BaseMapper<InspectionPlanDevice> {

    List<InspectionPlanDevice> selectDevicesByPlanId(@Param("planId") Long planId);

    int insertPlanDevice(InspectionPlanDevice planDevice);

    int deletePlanDevice(@Param("planId") Long planId, @Param("deviceId") Long deviceId);

    int deleteDevicesByPlanId(@Param("planId") Long planId);

    int batchInsertPlanDevice(@Param("list") List<InspectionPlanDevice> list);

    int countByPlanId(@Param("planId") Long planId);
}
