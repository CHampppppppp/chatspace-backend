package com.chat.controller;

import com.chat.entity.AiRole;
import com.chat.service.AiRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class AiRoleController {

    @Resource
    private AiRoleService aiRoleService;

    /**
     * 获取所有AI角色列表接口
     * 用于AI社区展示，所有人可见
     */
    @GetMapping("/myai/list")
    public Map<String, Object> getAllAiRoles() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<AiRole> aiRoles = aiRoleService.getAllAiRoles();
            
            if (aiRoles != null) {              
                response.put("code", 200);
                response.put("msg", "获取AI列表成功");
                response.put("data", aiRoles);
            } else {
                response.put("code", 400);
                response.put("msg", "获取AI列表失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 获取用户对AI的点赞状态
     * @param aiId AI角色ID
     * @param userId 用户ID
     * @return 响应结果
     */
    @GetMapping("/like/status/{aiId}/{userId}")
    public Map<String, Object> getLikeStatus(@PathVariable Long aiId, @PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isLiked = aiRoleService.getLikeStatus(userId, aiId);
            int likeCount = aiRoleService.getLikeCount(aiId);
            
            response.put("code", 200);
            response.put("msg", "获取点赞状态成功");
            response.put("data", Map.of(
                "isLiked", isLiked,
                "likeCount", likeCount
            ));
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        return response;
    }

    /**
     * 为AI角色点赞
     * @param aiId AI角色ID
     * @param userId 用户ID
     * @return 响应结果
     */
    @PostMapping("/like/{aiId}/{userId}")
    public Map<String, Object> likeAiRole(@PathVariable Long aiId, @PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = aiRoleService.likeAiRole(userId, aiId);
            if (success) {
                response.put("code", 200);
                response.put("msg", "点赞成功");
                // 返回最新的点赞数
                int likeCount = aiRoleService.getLikeCount(aiId);
                response.put("data", Map.of("likeCount", likeCount));
            } else {
                response.put("code", 400);
                response.put("msg", "点赞失败，已经点赞过了或AI角色不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        return response;
    }

    /**
     * 取消AI角色点赞
     * @param aiId AI角色ID
     * @param userId 用户ID
     * @return 响应结果
     */
    @DeleteMapping("/like/{aiId}/{userId}")
    public Map<String, Object> unlikeAiRole(@PathVariable Long aiId, @PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = aiRoleService.unlikeAiRole(userId, aiId);
            if (success) {
                response.put("code", 200);
                response.put("msg", "取消点赞成功");
                // 返回最新的点赞数
                int likeCount = aiRoleService.getLikeCount(aiId);
                response.put("data", Map.of("likeCount", likeCount));
            } else {
                response.put("code", 400);
                response.put("msg", "取消点赞失败，用户未点赞过该AI");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        return response;
    }

    /**
     * 根据AI ID获取AI角色信息
     */
    @GetMapping("/myai/{aiId}")
    public Map<String, Object> getAiRoleById(@PathVariable Long aiId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            AiRole aiRole = aiRoleService.getAiRoleById(aiId);
            
            if (aiRole != null) {
                response.put("code", 200);
                response.put("msg", "获取AI信息成功");
                response.put("data", aiRole);
            } else {
                response.put("code", 400);
                response.put("msg", "AI不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }

    /**
     * 根据创建者ID获取AI角色列表
     */
    @GetMapping("/{username}/myai")
    public Map<String, Object> getAiRolesByUserId(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<AiRole> aiRoles = aiRoleService.getAiRolesByCreatedBy(username);
            
            if (aiRoles != null) {
                response.put("code", 200);
                response.put("msg", "获取用户AI列表成功");
                response.put("data", aiRoles);
            } else {
                response.put("code", 400);
                response.put("msg", "获取用户AI列表失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "服务器内部错误");
        }
        
        return response;
    }
}