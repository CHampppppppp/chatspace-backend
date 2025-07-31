package com.chat.mapper;

import com.chat.entity.FriendRequest;
import com.chat.entity.Friendship;
import com.chat.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendMapper {

    // 添加好友请求
    @Insert("INSERT INTO friend_requests(sender_id, receiver_id, request_message) VALUES(#{senderId}, #{receiverId}, #{requestMessage})")
    @Options(useGeneratedKeys = true, keyProperty = "requestId")
    void insertFriendRequest(FriendRequest friendRequest);

    // 查询用户是否存在
    @Select("SELECT user_id FROM users WHERE user_id = #{userId}")
    Long findUserById(@Param("userId") Long userId);

    // 通过用户名查询用户
    @Select("SELECT user_id FROM users WHERE username = #{username}")
    Long findUserByUsername(@Param("username") String username);

    // 查询是否已是好友
    @Select("SELECT COUNT(*) FROM friends WHERE (user_id = #{userId} AND friend_id = #{friendId})")
    int areFriends(@Param("userId") Long userId, @Param("friendId") Long friendId);

    // 查询是否存在待处理的好友请求
    @Select("SELECT COUNT(*) FROM friend_requests WHERE (sender_id = #{senderId} AND receiver_id = #{receiverId} AND status = 'pending') OR (sender_id = #{receiverId} AND receiver_id = #{senderId} AND status = 'pending')")
    int hasPendingRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    // 获取好友申请列表
    @Select("SELECT * FROM friend_requests WHERE receiver_id = #{userId} AND status = 'pending'")
    List<FriendRequest> getFriendRequestsByReceiverId(@Param("userId") Long userId);

    // 根据请求ID查询好友请求
    @Select("SELECT * FROM friend_requests WHERE request_id = #{requestId} AND status = 'pending'")
    FriendRequest getPendingRequestById(@Param("requestId") Long requestId);

    // 更新好友请求状态
    @Update("UPDATE friend_requests SET status = #{status} WHERE request_id = #{requestId}")
    void updateFriendRequestStatus(@Param("requestId") Long requestId, @Param("status") String status);

    // 插入好友关系（双向）
    @Insert("INSERT INTO friends(user_id, friend_id) VALUES(#{userId}, #{friendId})")
    void insertFriendship(@Param("userId") Long userId, @Param("friendId") Long friendId);

    // 获取好友列表
    @Select("SELECT friend_id, created_at, description FROM friends WHERE user_id = #{userId}")
    List<Friendship> findFriendsByUserId(@Param("userId") Long userId);


    // 删除好友关系
    @Delete("DELETE FROM friends WHERE (user_id = #{userId} AND friend_id = #{friendId}) OR (user_id = #{friendId} AND friend_id = #{userId})")
    void deleteFriendship(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_id, username, password, role, email, avatar, status, created_at, lastseen, age, gender, signature,is_blocked FROM users WHERE user_id = #{userId}")
    User getUserById(Long userId);
}