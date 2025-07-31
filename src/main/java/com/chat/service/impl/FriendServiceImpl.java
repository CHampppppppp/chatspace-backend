package com.chat.service.impl;

import com.chat.DTO.FriendshipDTO;
import com.chat.DTO.UserDTO;
import com.chat.entity.FriendRequest;
import com.chat.entity.Friendship;
import com.chat.entity.User;
import com.chat.mapper.FriendMapper;
import com.chat.service.FriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    @Transactional
    public String sendFriendRequest(Long senderId, String receiverUsername, String message) {
        // 1. 检查发送方和接收方是否存在
        Long receiverId = friendMapper.findUserByUsername(receiverUsername);
        if (friendMapper.findUserById(senderId) == null) {
            return "发送方用户不存在";
        }
        if (receiverId == null) {
            return "接收方用户不存在";
        }

        // 2. 检查是否已经是好友
        if (friendMapper.areFriends(senderId, receiverId) > 0) {
            return "你们已经是好友了";
        }

        // 3. 检查是否已有待处理的好友请求
        if (friendMapper.hasPendingRequest(senderId, receiverId) > 0) {
            return "已有待处理的好友请求";
        }

        // 4. 插入好友请求
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(senderId);
        friendRequest.setReceiverId(receiverId);
        friendRequest.setRequestMessage(message);
        friendMapper.insertFriendRequest(friendRequest);

        return "好友请求已发送";
    }

    @Override
    public List<FriendRequest> getFriendRequests(Long userId) {
        return friendMapper.getFriendRequestsByReceiverId(userId);
    }

    @Override
    @Transactional
    public String handleFriendRequest(Long requestId, String action) {
        FriendRequest request = friendMapper.getPendingRequestById(requestId);
        if (request == null) {
            return "好友请求不存在或已处理";
        }

        if ("accept".equalsIgnoreCase(action)) {
            // 接受请求，更新状态并添加好友
            friendMapper.updateFriendRequestStatus(requestId, "accepted");
            // 插入双向好友关系
            friendMapper.insertFriendship(request.getSenderId(), request.getReceiverId());
            friendMapper.insertFriendship(request.getReceiverId(), request.getSenderId());
            return "好友申请已接受";
        } else if ("reject".equalsIgnoreCase(action)) {
            // 拒绝请求，更新状态
            friendMapper.updateFriendRequestStatus(requestId, "rejected");
            return "好友申请已拒绝";
        } else {
            return "无效的操作";
        }
    }

    @Override
    public List<FriendshipDTO> getFriendList(Long userId) {
        List<Friendship> friendships = friendMapper.findFriendsByUserId(userId);
        return friendships.stream().map(friendship -> {
            FriendshipDTO dto = new FriendshipDTO();
            BeanUtils.copyProperties(friendship, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO getFriendInfo(Long friendId) {
        User user = friendMapper.getUserById(friendId);
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    @Transactional
    public String deleteFriend(Long currentUserId, Long friendId) {
        if (friendMapper.areFriends(currentUserId, friendId) == 0) {
            return "你们不是好友关系";
        }
        friendMapper.deleteFriendship(currentUserId, friendId);
        return "删除成功";
    }
}