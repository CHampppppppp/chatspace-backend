package com.chat.controller;

import com.chat.mapper.AdminMapper;
import com.chat.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员模块
 */
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;


    /**
     * 获取用户列表 (实际是获取单个用户信息)
     * @return
     */
    @GetMapping("/{userId}") // 此映射现在是查看用户信息所独有的
    public Map<String, Object> getUserInfo(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        com.chat.dto.UserDTO userDTO = adminService.getUserById(userId);

        if (userDTO != null) {
            response.put("code", 200);
            response.put("msg", "返回成功");
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", userDTO.getUserId());
            userData.put("username", userDTO.getUsername());
            userData.put("role", userDTO.getRole());
            userData.put("email", userDTO.getEmail());
            userData.put("avatar", userDTO.getAvatar());
            userData.put("status", userDTO.getStatus());
            userData.put("created_at", userDTO.getCreatedAt() != null ? userDTO.getCreatedAt().toString() : null);
            userData.put("lastseen", userDTO.getLastSeen() != null ? userDTO.getLastSeen().toString() : null);
            userData.put("age", userDTO.getAge());
            userData.put("gender", userDTO.getGender());
            userData.put("signature", userDTO.getSignature());
            response.put("data", userData);
        } else {
            response.put("code", 400);
            response.put("msg", "没找到用户");
        }
        return response;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            adminService.deleteUser(userId);
            response.put("code", 200);
            response.put("message", "删除成功");
        } catch (Exception e) {
            response.put("code", 400);
            response.put("message", "删除失败" + e.getMessage());
        }
        return response;
    }

    /**
     * 更新密码
     * @param userId
     * @param requestBody
     * @return
     */
    @PutMapping("/{userId}/password")
    public Map<String, Object> updatePassword(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        String newPassword = requestBody.get("password");

        if (newPassword == null || newPassword.trim().isEmpty()) {
            response.put("code", 400); // Bad Request
            response.put("msg", "密码不为空");
            return response;
        }

        try {
            adminService.updatePassword(userId, newPassword);
            response.put("code", 200);
            response.put("msg", "密码更新成功");
            Map<String, String> data = new HashMap<>();
            data.put("password", newPassword);
            response.put("data", data);
        } catch (Exception e) {
            response.put("code", 400); //
            response.put("msg", "密码更新失败" + e.getMessage());
        }
        return response;
    }
}