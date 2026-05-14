package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    IPage<SysUser> selectUserPage(Page<SysUser> page, @Param("username") String username, 
                                  @Param("nickname") String nickname, @Param("status") String status,
                                  @Param("deptId") Long deptId);
    
    SysUser selectUserByUsername(@Param("username") String username);
    
    List<SysUser> selectUsersByRoleId(@Param("roleId") Long roleId);
    
    int deleteUserRoles(@Param("userId") Long userId);
    
    int insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
