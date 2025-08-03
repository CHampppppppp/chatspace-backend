package com.chat.service;


import java.util.Map;

import com.chat.dto.admin.UpdateUserInfoRequestAdmin;
import com.chat.dto.user.UserDTO;

public interface AdminService {
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
    void updatePassword(Long userId, String newPassword);
    public UserDTO updateUserInfoAdmin(UpdateUserInfoRequestAdmin updateRequest);
    void toggleBlockStatus(Long userId);
    Map<String, Object> getUserList();
}