package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dev_device_attachment")
public class DeviceAttachment extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long deviceId;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private String fileExtension;

    private String uploadType;

    private Long uploadedBy;

    private LocalDateTime uploadTime;

    private String description;
}
