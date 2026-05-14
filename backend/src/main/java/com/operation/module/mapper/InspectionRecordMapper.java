package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface InspectionRecordMapper extends BaseMapper<InspectionRecord> {

    IPage<InspectionRecord> selectRecordPage(Page<InspectionRecord> page,
                                             @Param("projectId") Long projectId,
                                             @Param("deviceId") Long deviceId,
                                             @Param("inspectorId") Long inspectorId,
                                             @Param("inspectionResult") String inspectionResult,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);

    InspectionRecord selectRecordById(@Param("id") Long id);

    List<InspectionRecord> selectRecordsByTaskId(@Param("taskId") Long taskId);

    List<InspectionRecord> selectRecordsByDeviceId(@Param("deviceId") Long deviceId);

    int insertRecord(InspectionRecord record);

    int updateRecord(InspectionRecord record);

    List<Map<String, Object>> selectInspectionStats(@Param("projectId") Long projectId,
                                                     @Param("startTime") String startTime,
                                                     @Param("endTime") String endTime);
}
