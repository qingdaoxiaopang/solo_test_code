package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.DeviceLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeviceLogMapper extends BaseMapper<DeviceLog> {

    List<DeviceLog> selectLogsByDeviceId(@Param("deviceId") Long deviceId);

    int insertLog(DeviceLog log);

    int deleteLog(@Param("id") Long id);
}
