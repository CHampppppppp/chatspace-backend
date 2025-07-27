package com.chat.controller;

import com.chat.dto.user.ChangePasswordRequest;
import com.chat.dto.user.LoginRequest;
import com.chat.dto.user.LogoutRequest;
import com.chat.dto.user.RegisterRequest;
import com.chat.dto.user.UpdateUserInfoRequest;
import com.chat.dto.user.UserDTO;
import com.chat.entity.User;
import com.chat.mapper.UserMapper;
import com.chat.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    
    @Resource
    private UserMapper userMapper;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        UserDTO userDTO = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        Map<String, Object> response = new HashMap<>();

        if (userDTO != null) {
            response.put("code", 200);
            response.put("msg", "登录成功");
            response.put("data", userDTO);
        } else {
            response.put("code", 400);
            response.put("msg", "用户名或密码错误");
        }

        return response;
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest registerRequest) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());

        if (success) {
            response.put("code", 200);
            response.put("msg", "注册成功");
        } else {
            response.put("code", 400);
            response.put("msg", "注册失败，用户名可能已存在或数据错误");
        }
        return response;
    }

    /**
     * 更新用户信息接口
     */
    @PostMapping("/user/info")
    public Map<String, Object> updateUserInfo(@RequestBody UpdateUserInfoRequest updateRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserDTO updatedUser = userService.updateUserInfo(updateRequest);
            
            if (updatedUser != null) {
                response.put("code", 200);
                response.put("msg", "个人信息已保存");
                response.put("data", updatedUser);
            } else {
                response.put("code", 400);
                response.put("msg", "更新失败，用户不存在或数据错误");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 修改密码接口
     */
    @PostMapping("/user/password")
    public Map<String, Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = userService.changePassword(
                changePasswordRequest.getUserId(),
                changePasswordRequest.getCurrentPassword(),
                changePasswordRequest.getNewPassword()
            );
            
            if (success) {
                response.put("code", 200);
                response.put("msg", "密码修改成功");
            } else {
                response.put("code", 400);
                response.put("msg", "密码修改失败，请检查当前密码是否正确");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 退出登录接口
     */
    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestBody LogoutRequest logoutRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = userService.logout(logoutRequest.getUserId());
            
            if (success) {
                response.put("code", 200);
                response.put("msg", "退出登录成功");
            } else {
                response.put("code", 400);
                response.put("msg", "退出登录失败，用户不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 获取个人资料接口
     */
    @GetMapping("/{userId}")
    public Map<String, Object> getUserInfo(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = userMapper.findByUserId(userId);
            
            if (user != null) {
                // 将 User 转为 UserDTO
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(user.getUserId());
                userDTO.setUsername(user.getUsername());
                userDTO.setRole(user.getRole());
                userDTO.setEmail(user.getEmail());
                userDTO.setAvatar(user.getAvatar());
                userDTO.setStatus(user.getStatus());
                userDTO.setCreatedAt(user.getCreatedAt());
                userDTO.setLastSeen(user.getLastSeen());
                userDTO.setAge(user.getAge());
                userDTO.setGender(user.getGender());
                userDTO.setSignature(user.getSignature());
                
                response.put("code", 200);
                response.put("msg", "获取用户信息成功");
                response.put("data", userDTO);
            } else {
                response.put("code", 400);
                response.put("msg", "用户不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 删除账号接口
     */
    @DeleteMapping("/{userId}")
    public Map<String, Object> deleteAccount(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = userService.deleteAccount(userId);
            
            if (success) {
                response.put("code", 200);
                response.put("msg", "账号已成功注销");
            } else {
                response.put("code", 400);
                response.put("msg", "注销失败，用户不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }
}