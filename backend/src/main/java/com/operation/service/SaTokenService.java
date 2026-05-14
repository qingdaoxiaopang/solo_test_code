package com.operation.service;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaTokenService implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissions = new ArrayList<>();
        permissions.add("*");
        return permissions;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        return roles;
    }
}
