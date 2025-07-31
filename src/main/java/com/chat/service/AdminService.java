package com.chat.service;


import com.chat.DTO.UserDTO;

import java.util.Map;

public interface AdminService {
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
    void updatePassword(Long userId, String newPassword);
    void toggleBlockStatus(Long userId);
    Map<String, Object> getUserList();
}