package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.DeviceParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeviceParamMapper extends BaseMapper<DeviceParam> {

    List<DeviceParam> selectParamsByDeviceId(@Param("deviceId") Long deviceId);

    int insertDeviceParam(DeviceParam param);

    int updateDeviceParam(DeviceParam param);

    int deleteDeviceParam(@Param("id") Long id);

    int deleteParamsByDeviceId(@Param("deviceId") Long deviceId);

    int batchInsertParam(@Param("list") List<DeviceParam> list);
}
