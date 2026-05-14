package com.operation.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    
    private String username;
    
    private String nickname;
    
    private String avatar;
    
    private Long deptId;
    
    private String deptName;
}
