package com.chat.service;


import com.chat.dto.UserDTO;

public interface AdminService {
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
    void updatePassword(Long userId, String newPassword);
}