package com.chat.mapper;

import com.chat.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_id, username, password, role, email, avatar, status, created_at, lastseen, age, gender, signature,is_blocked FROM users WHERE user_id = #{userId}")
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
     * 切换用户封禁状态
     * @param userId
     * @param isBlocked 传入 1 表示封禁，0 表示解除封禁
     */
    @Update(value = "UPDATE users SET is_blocked = #{isBlocked} WHERE user_id = #{userId}")
    int updateIsBlocked(@Param("userId") Long userId, @Param("isBlocked") int isBlocked);

    /**
     * 获取所有用户信息
     * @return
     */
    @Select("SELECT user_id, username, avatar, status, is_blocked FROM users")
    List<User> getAllUsers();

    /**
     * 获取用户总数
     * @return
     */
    @Select("SELECT COUNT(*) FROM users")
    int getTotalUserCount();

}
