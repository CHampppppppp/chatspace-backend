package com.chat.service.impl;

import com.chat.dto.user.UpdateUserInfoRequest;
import com.chat.dto.user.UserDTO;
import com.chat.entity.User;
import com.chat.mapper.UserMapper;
import com.chat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     */
    @Override
    public UserDTO login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }


    // System.out.println("=== findByUsername 返回的数据 ===");
    // if (user != null) {
    //     System.out.println("userId: " + user.getUserId());
    //     System.out.println("username: " + user.getUsername());
    //     System.out.println("role: " + user.getRole());
    //     System.out.println("email: " + user.getEmail());
    //     System.out.println("avatar: " + user.getAvatar());
    //     System.out.println("status: " + user.getStatus());
    //     System.out.println("createdAt: " + user.getCreatedAt());
    //     System.out.println("lastSeen: " + user.getLastSeen());
    //     System.out.println("age: " + user.getAge());
    //     System.out.println("gender: " + user.getGender());
    //     System.out.println("signature: " + user.getSignature());
    //     System.out.println("isBlocked: " + user.getIsBlocked());
    // } else {
    //     System.out.println("用户不存在或查询结果为null");
    // }
    // System.out.println("================================");
    
        // 将 User 转为 UserDTO（手动赋值）
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setStatus("online");
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastSeen(user.getLastSeen());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setSignature(user.getSignature());

        return dto;
    }

    /**
     * 注册新用户
     */
    @Override
    public boolean register(String username, String password, String email) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(username) != null) {
            return false; // 用户名已存在
        }

        User newUser = new User();
        // 生成随机用户ID：时间戳 + 随机数
        long timestamp = System.currentTimeMillis();
        long userId = Long.parseLong(String.valueOf(timestamp));
        newUser.setUserId(userId);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("user");
        newUser.setEmail(email);
        newUser.setAvatar("https://i.pinimg.com/736x/69/41/8a/69418a31559fdf600b091164715a9cf4.jpg");
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newUser.setStatus("offline");
        newUser.setIsBlocked(0);

        try {
            userMapper.insertUser(newUser);
            return true;
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新用户信息
     */
    @Override
    public UserDTO updateUserInfo(UpdateUserInfoRequest updateRequest) {
        // 先查询用户是否存在
        User existingUser = userMapper.findByUserId(updateRequest.getUserId());
        if (existingUser == null) {
            return null;
        }

        // 更新用户信息
        User updateUser = new User();
        updateUser.setUserId(updateRequest.getUserId()); // 设置userId用于WHERE条件
        updateUser.setUsername(updateRequest.getUsername());
        updateUser.setEmail(updateRequest.getEmail());
        updateUser.setAvatar(updateRequest.getAvatar()); 
        updateUser.setAge(updateRequest.getAge());
        updateUser.setGender(updateRequest.getGender());
        updateUser.setSignature(updateRequest.getSignature());

        try {
            int result = userMapper.updateUserInfo(updateUser);
            if (result > 0) {
                // 更新成功，返回更新后的用户信息
                User updatedUser = userMapper.findByUserId(updateRequest.getUserId());
                
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
     * 修改密码
     */
    @Override
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        try {
            // 先查询用户是否存在
            User user = userMapper.findByUserId(userId);
            if (user == null) {
                return false; // 用户不存在
            }

            // 验证当前密码是否正确
            if (!user.getPassword().equals(currentPassword)) {
                return false; // 当前密码错误
            }

            // 检查新密码是否与当前密码相同
            if (currentPassword.equals(newPassword)) {
                return false; // 新密码不能与当前密码相同
            }

            // 更新密码
            int result = userMapper.updatePassword(userId, newPassword);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户退出登录
     */
    @Override
    public boolean logout(Long userId) {
        try {
            // 先查询用户是否存在
            User user = userMapper.findByUserId(userId);
            if (user == null) {
                return false; // 用户不存在
            }

            // 更新用户状态为offline，并设置最后在线时间为当前时间
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            int result = userMapper.updateUserStatus(userId, "offline", currentTime);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户账号
     */
    @Override
    public boolean deleteAccount(Long userId) {
        try {
            // 先查询用户是否存在
            User user = userMapper.findByUserId(userId);
            if (user == null) {
                return false; // 用户不存在
            }

            // 删除用户账号
            int result = userMapper.deleteUser(userId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}