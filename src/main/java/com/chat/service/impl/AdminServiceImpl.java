package com.chat.service.impl;

import com.chat.mapper.AdminMapper;
import com.chat.service.AdminService;
import com.chat.dto.UserDTO;
import com.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}