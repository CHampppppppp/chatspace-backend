package com.chat.service;

import com.chat.DTO.FriendRequestDTO;
import com.chat.DTO.FriendshipDTO;
import com.chat.DTO.UserDTO;
import com.chat.entity.FriendRequest;

import java.util.List;

public interface FriendService {
    // 1. 添加好友
    String sendFriendRequest(Long senderId, String receiverUsername, String message);

    // 2. 获取好友申请列表
    List<FriendRequest> getFriendRequests(Long userId);

    // 3. 拒绝或接受好友申请
    String handleFriendRequest(Long requestId, String action);

    // 4. 获取好友列表
    List<FriendshipDTO> getFriendList(Long userId);

    // 5. 查看好友信息
    UserDTO getFriendInfo(Long friendId);

    // 6. 删除好友
    String deleteFriend(Long currentUserId, Long friendId);
}