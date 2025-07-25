package com.chat.service.impl;

import com.chat.entity.AiLike;
import com.chat.entity.AiRole;
import com.chat.mapper.AiLikeMapper;
import com.chat.mapper.AiRoleMapper;
import com.chat.service.AiRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRoleServiceImpl implements AiRoleService {

    @Resource
    private AiRoleMapper aiRoleMapper;

    @Resource
    private AiLikeMapper aiLikeMapper;

    /**
     * 获取所有AI角色列表
     */
    @Override
    public List<AiRole> getAllAiRoles() {
        try {
            return aiRoleMapper.findAllAiRoles();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据AI ID获取AI角色信息
     */
    @Override
    public AiRole getAiRoleById(Long aiId) {
        try {
            return aiRoleMapper.findByAiId(aiId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据创建者ID获取AI角色列表
     */
    @Override
    public List<AiRole> getAiRolesByCreatedBy(String username) {
        try {
            return aiRoleMapper.findByCreatedBy(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean likeAiRole(Long userId, Long aiId) {
        try {
            // 检查是否已经点赞
            AiLike existingLike = aiLikeMapper.findByUserIdAndAiId(userId, aiId);
            if (existingLike != null) {
                return false; // 已经点赞过了
            }
            
            // 创建点赞记录
            AiLike aiLike = new AiLike();
            aiLike.setUserId(userId);
            aiLike.setAiId(aiId);
            
            int result = aiLikeMapper.insertLike(aiLike);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("点赞失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean unlikeAiRole(Long userId, Long aiId) {
        try {
            int result = aiLikeMapper.deleteLike(userId, aiId);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("取消点赞失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean getLikeStatus(Long userId, Long aiId) {
        try {
            AiLike aiLike = aiLikeMapper.findByUserIdAndAiId(userId, aiId);
            return aiLike != null;
        } catch (Exception e) {
            throw new RuntimeException("获取点赞状态失败: " + e.getMessage(), e);
        }
    }

    @Override
    public int getLikeCount(Long aiId) {
        try {
            return aiLikeMapper.countLikesByAiId(aiId);
        } catch (Exception e) {
            throw new RuntimeException("获取点赞数失败: " + e.getMessage(), e);
        }
    }
}