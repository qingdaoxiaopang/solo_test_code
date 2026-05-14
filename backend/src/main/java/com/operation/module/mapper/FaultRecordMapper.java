package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.FaultRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface FaultRecordMapper extends BaseMapper<FaultRecord> {

    IPage<FaultRecord> selectFaultPage(Page<FaultRecord> page,
                                       @Param("projectId") Long projectId,
                                       @Param("faultNo") String faultNo,
                                       @Param("faultType") String faultType,
                                       @Param("faultLevel") String faultLevel,
                                       @Param("faultStatus") String faultStatus,
                                       @Param("deviceId") Long deviceId);

    List<FaultRecord> selectFaultList(@Param("projectId") Long projectId,
                                       @Param("faultStatus") String faultStatus);

    FaultRecord selectFaultById(@Param("id") Long id);

    FaultRecord selectFaultByNo(@Param("faultNo") String faultNo);

    int insertFault(FaultRecord fault);

    int updateFault(FaultRecord fault);

    int deleteFault(@Param("id") Long id);

    List<Map<String, Object>> selectFaultStats(@Param("projectId") Long projectId,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime);

    List<FaultRecord> selectFaultKnowledge(@Param("faultType") String faultType,
                                           @Param("deviceType") String deviceType);
}
