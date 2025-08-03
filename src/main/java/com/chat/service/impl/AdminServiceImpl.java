package com.chat.service.impl;

import com.chat.dto.admin.UpdateUserInfoRequestAdmin;
import com.chat.dto.user.UpdateUserInfoRequest;
import com.chat.mapper.AdminMapper;
import com.chat.service.AdminService;
import com.chat.dto.user.UserDTO;
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
     * 管理员更新用户信息
     */
    @Override
    public UserDTO updateUserInfoAdmin(UpdateUserInfoRequestAdmin updateRequest) {
        // 先查询用户是否存在
        User existingUser = adminMapper.getUserById(updateRequest.getUserId());
        if (existingUser == null) {
            return null;
        }

        // 更新用户信息
        User updateUser = new User();
        updateUser.setUserId(updateRequest.getUserId()); // 添加这一行！
        updateUser.setUsername(updateRequest.getUsername());
        updateUser.setEmail(updateRequest.getEmail());
        updateUser.setAvatar(updateRequest.getAvatar());
        updateUser.setRole(updateRequest.getRole());
        updateUser.setStatus(updateRequest.getStatus());
        updateUser.setIsBlocked(updateRequest.getIsBlocked());

        try {
            int result = adminMapper.updateUserInfoAdmin(updateUser);
            if (result > 0) {
                // 更新成功，返回更新后的用户信息
                User updatedUser = adminMapper.getUserById(updateRequest.getUserId());

                // 转换为 UserDTO（完整映射所有字段）
                UserDTO dto = new UserDTO();
                dto.setUserId(updatedUser.getUserId());
                dto.setUsername(updatedUser.getUsername());
                dto.setRole(updatedUser.getRole());
                dto.setEmail(updatedUser.getEmail());
                dto.setAvatar(updatedUser.getAvatar());
                dto.setStatus(updatedUser.getStatus());
                dto.setCreatedAt(updatedUser.getCreatedAt());
                dto.setLastSeen(updatedUser.getLastSeen());
                dto.setAge(updatedUser.getAge());
                dto.setGender(updatedUser.getGender());
                dto.setSignature(updatedUser.getSignature());
                dto.setIsBlocked(updatedUser.getIsBlocked());

                return dto;
            } else {
                return null; // 更新失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
