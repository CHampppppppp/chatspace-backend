package com.chat.service;

import com.chat.DTO.UserDTO;
import com.chat.dto.UpdateUserInfoRequest;


public interface UserService {
    /**
     * 登录方法：返回脱敏后的用户 DTO
     */
    UserDTO login(String username, String password);

    /**
     * 注册方法：将新用户数据存入数据库
     */
    boolean register(String username, String password, String email);

    /**
     * 更新用户信息
     * @param updateRequest 更新请求对象
     * @return 更新后的用户信息
     */
    UserDTO updateUserInfo(UpdateUserInfoRequest updateRequest);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String currentPassword, String newPassword);

    /**
     * 用户退出登录
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean logout(Long userId);

    /**
     * 删除用户账号
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean deleteAccount(Long userId);
}