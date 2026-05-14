package com.operation.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
public class SysLog extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String logType;
    
    private String logContent;
    
    private String operationType;
    
    private String requestMethod;
    
    private String requestUrl;
    
    private String requestParams;
    
    private String responseResult;
    
    private String ip;
    
    private String location;
    
    private String userAgent;
    
    private Long userId;
    
    private String username;
    
    private Integer costTime;
    
    private String status;
    
    private String errorMsg;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
