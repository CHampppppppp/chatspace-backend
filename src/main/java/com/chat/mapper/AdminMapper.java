package com.chat.mapper;

import com.chat.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_id, username, password, role, email, avatar, status, created_at, lastseen, age, gender, signature FROM users WHERE user_id = #{userId}")
    User getUserById(Long userId);

    /**
     * 删除用户
     * @param userId
     */
    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void deleteUserById(Long userId);

    /**
     * 更新用户密码
     * @param userId
     * @param newPassword
     */
    @Update("UPDATE users SET password = #{newPassword} WHERE user_id = #{userId}")
    void updatePassword(Long userId, String newPassword);

    /**
     * 封禁/解封用户
     * @param userId
     * @param status
     */
    @Update("UPDATE users SET status = #{status} WHERE user_id = #{userId}")
    void updateStatus(Long userId, Integer status);
}
