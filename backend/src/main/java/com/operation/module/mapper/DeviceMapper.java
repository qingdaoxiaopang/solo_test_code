package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    IPage<Device> selectDevicePage(Page<Device> page,
                                   @Param("projectId") Long projectId,
                                   @Param("deviceName") String deviceName,
                                   @Param("deviceCode") String deviceCode,
                                   @Param("deviceType") String deviceType,
                                   @Param("status") String status);

    List<Device> selectDeviceList(@Param("projectId") Long projectId,
                                   @Param("deviceName") String deviceName,
                                   @Param("deviceCode") String deviceCode,
                                   @Param("deviceType") String deviceType,
                                   @Param("status") String status);

    Device selectDeviceById(@Param("id") Long id);

    int insertDevice(Device device);

    int updateDevice(Device device);

    int deleteDevice(@Param("id") Long id);

    int batchInsertDevice(@Param("list") List<Device> list);

    List<Device> selectDevicesByProjectId(@Param("projectId") Long projectId);
}
