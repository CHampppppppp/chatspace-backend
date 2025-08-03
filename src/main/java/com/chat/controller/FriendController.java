package com.chat.controller;

import com.chat.dto.friend.FriendRequestDTO;
import com.chat.dto.friend.FriendshipDTO;
import com.chat.dto.user.UserDTO;
import com.chat.entity.FriendRequest;
import com.chat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    // 1. 添加好友
    @PostMapping("/friend/request")
    public ResponseEntity<Map<String, Object>> addFriendRequest(@RequestBody Map<String, Object> requestBody) {
        Long senderId = Long.valueOf(requestBody.get("sender_id").toString());
        String receiverUsername = (String) requestBody.get("reciever_username");
        String message = (String) requestBody.get("message");

        String resultMsg = friendService.sendFriendRequest(senderId, receiverUsername, message);

        Map<String, Object> response = new HashMap<>();
        if (resultMsg.contains("成功") || resultMsg.contains("已发送")) {
            response.put("code", 200);
            response.put("msg", resultMsg);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400); // Bad Request
            response.put("msg", resultMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 2. 获取好友申请列表
    @GetMapping("/{user_id}/friend-request-list")
    public ResponseEntity<Map<String, Object>> getFriendRequestList(@PathVariable("user_id") Long userId) {
        List<FriendRequest> requests = friendService.getFriendRequests(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "获取成功");

        List<FriendRequestDTO> requestDTOs = requests.stream().map(req -> {
            FriendRequestDTO dto = new FriendRequestDTO();
            // 假设BeanUtils已配置或手动拷贝
            dto.setRequestId(req.getRequestId());
            dto.setSenderId(req.getSenderId());
            dto.setReceiverId(req.getReceiverId());
            dto.setRequestMessage(req.getRequestMessage());
            dto.setStatus(req.getStatus());
            dto.setCreatedAt(req.getCreatedAt());
            dto.setUpdatedAt(req.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());

        response.put("data", requestDTOs);
        return ResponseEntity.ok(response);
    }

    // 3. 拒绝或接受好友申请
    @PostMapping("/friend-request/{request_id}")
    public ResponseEntity<Map<String, Object>> handleFriendRequest(@PathVariable("request_id") Long requestId, @RequestBody Map<String, String> requestBody) {
        String action = requestBody.get("action");
        String resultMsg = friendService.handleFriendRequest(requestId, action);

        Map<String, Object> response = new HashMap<>();
        if (resultMsg.contains("已接受") || resultMsg.contains("已拒绝")) {
            response.put("code", 200);
            response.put("msg", resultMsg);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400);
            response.put("msg", resultMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 4. 获取好友列表
    @GetMapping("/{user_id}/friend-list")
    public ResponseEntity<Map<String, Object>> getFriendList(@PathVariable("user_id") Long userId) {
        List<FriendshipDTO> friends = friendService.getFriendList(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "获取成功");
        response.put("data", friends);
        return ResponseEntity.ok(response);
    }

    // 5. 查看好友信息
    @GetMapping("/{friend_id}")
    public ResponseEntity<Map<String, Object>> getFriendInfo(@PathVariable("friend_id") Long friendId) {
        UserDTO friendInfo = friendService.getFriendInfo(friendId);

        Map<String, Object> response = new HashMap<>();
        if (friendInfo != null) {
            response.put("code", 200);
            response.put("msg", "获取成功");
            response.put("data", friendInfo);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400);
            response.put("msg", "好友信息不存在");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 6. 删除好友
    @DeleteMapping("/{friend_id}")
    public ResponseEntity<Map<String, Object>> deleteFriend(@PathVariable("friend_id") Long friendId) {
        // 会话或JWT中获取当前登录用户的ID
        // 这里为了简化，我们假设一个固定的当前用户ID，例如1L
        Long currentUserId = 1L;

        String resultMsg = friendService.deleteFriend(currentUserId, friendId);

        Map<String, Object> response = new HashMap<>();
        if (resultMsg.contains("成功")) {
            response.put("code", 200);
            response.put("msg", resultMsg);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400);
            response.put("msg", resultMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}