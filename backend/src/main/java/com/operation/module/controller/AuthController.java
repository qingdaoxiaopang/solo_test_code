package com.operation.module.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.operation.common.entity.Result;
import com.operation.module.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private IAuthService authService;
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.success(authService.login(username, password));
    }
    
    @PostMapping("/logout")
    @SaCheckLogin
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }
    
    @GetMapping("/userinfo")
    @SaCheckLogin
    public Result<Object> getUserInfo() {
        return Result.success(authService.getUserInfo());
    }
    
    @PutMapping("/password")
    @SaCheckLogin
    public Result<Void> updatePassword(@RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        authService.updatePassword(oldPassword, newPassword);
        return Result.success();
    }
}
