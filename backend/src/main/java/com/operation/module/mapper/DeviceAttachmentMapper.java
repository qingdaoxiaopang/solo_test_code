package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.DeviceAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeviceAttachmentMapper extends BaseMapper<DeviceAttachment> {

    List<DeviceAttachment> selectAttachmentsByDeviceId(@Param("deviceId") Long deviceId);

    int insertAttachment(DeviceAttachment attachment);

    int deleteAttachment(@Param("id") Long id);

    int deleteAttachmentsByDeviceId(@Param("deviceId") Long deviceId);
}
