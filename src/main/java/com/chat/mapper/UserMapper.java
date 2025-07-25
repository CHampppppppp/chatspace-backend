package com.chat.mapper;

import com.chat.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 用户登录查询
     * @param username
     * @return
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    /**
     * 插入新用户
     * @param user
     */
    @Insert("INSERT INTO users (user_id, username, password, email, role, avatar, status, created_at, is_blocked) VALUES (#{userId}, #{username}, #{password}, #{email}, #{role}, #{avatar}, #{status}, #{createdAt}, #{isBlocked})")
    void insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     */
    @Update("UPDATE users SET username = #{username}, email = #{email}, avatar = #{avatar}, age = #{age}, gender = #{gender}, signature = #{signature} WHERE user_id = #{userId}")
    int updateUserInfo(User user);

    /**
     * 根据用户ID查询用户
     * @param userId
     * @return
     */
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User findByUserId(Long userId);

    /**
     * 更新用户密码
     * @param userId
     * @param newPassword
     * @return
     */
    @Update("UPDATE users SET password = #{newPassword} WHERE user_id = #{userId}")
    int updatePassword(Long userId, String newPassword);

    /**
     * 更新用户状态和最后在线时间
     * @param userId
     * @param status
     * @param lastSeen
     * @return
     */
    @Update("UPDATE users SET status = #{status}, last_seen = #{lastSeen} WHERE user_id = #{userId}")
    int updateUserStatus(Long userId, String status, java.sql.Timestamp lastSeen);

    /**
     * 删除用户账号
     * @param userId
     * @return
     */
    @Update("DELETE FROM users WHERE user_id = #{userId}")
    int deleteUser(Long userId);
}