package com.chat.service.impl;

import com.chat.DTO.UserDTO;
import com.chat.mapper.AdminMapper;
import com.chat.service.AdminService;
import com.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 【新增】导入 @Transactional
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 删除用户
     * @param userId
     */
    @Override
    public void deleteUser(Long userId) {
        adminMapper.deleteUserById(userId);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public UserDTO getUserById(Long userId) {
        User user = adminMapper.getUserById(userId);
        if (user != null) {
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
            userDTO.setIsBlocked(user.getIsBlocked());
            return userDTO;
        }
        return null;
    }

    /**
     * 更新密码
     * @param userId
     * @param newPassword
     */
    @Override
    public void updatePassword(Long userId, String newPassword) {
        adminMapper.updatePassword(userId, newPassword);
    }

    /**
     * 切换用户封禁状态
     * @param userId
     */
    @Override
    @Transactional
    public void toggleBlockStatus(Long userId) {
        User user = adminMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户ID不存在: " + userId);
        }

        int current = user.getIsBlocked();
        int newStatus = 1 - current;

        System.out.println("用户当前状态: " + current + "，将切换为: " + newStatus);

        int rows = adminMapper.updateIsBlocked(userId, newStatus);
        if (rows == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    /**
     * 获取用户列表
     * @return Map containing "total" and "list"
     */
    @Override
    public Map<String, Object> getUserList() {
        List<User> users = adminMapper.getAllUsers();
        int total = adminMapper.getTotalUserCount();

        List<Map<String, Object>> userListDTO = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("username", user.getUsername());
            userMap.put("avatar", user.getAvatar());
            userMap.put("status", user.getStatus());
            userMap.put("is_blocked", user.getIsBlocked());
            userListDTO.add(userMap);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("list", userListDTO);
        return result;
    }



}