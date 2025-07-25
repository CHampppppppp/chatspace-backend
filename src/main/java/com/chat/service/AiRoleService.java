package com.chat.service;

import com.chat.entity.AiRole;

import java.util.List;

public interface AiRoleService {
    
    /**
     * 获取所有AI角色列表
     * @return AI角色列表
     */
    List<AiRole> getAllAiRoles();
    
    /**
     * 根据AI ID获取AI角色信息
     * @param aiId AI ID
     * @return AI角色信息
     */
    AiRole getAiRoleById(Long aiId);
    
    /**
     * 根据创建者ID获取AI角色列表
     * @param createdBy 创建者ID
     * @return AI角色列表
     */
    List<AiRole> getAiRolesByCreatedBy(String createdBy);

    /**
     * 为AI角色点赞
     * @param userId 用户ID
     * @param aiId AI角色ID
     * @return 是否成功
     */
    boolean likeAiRole(Long userId, Long aiId);

    /**
     * 取消AI角色点赞
     * @param userId 用户ID
     * @param aiId AI角色ID
     * @return 是否成功
     */
    boolean unlikeAiRole(Long userId, Long aiId);

    /**
     * 获取用户对AI的点赞状态
     * @param userId 用户ID
     * @param aiId AI角色ID
     * @return 是否已点赞
     */
    boolean getLikeStatus(Long userId, Long aiId);

    /**
     * 获取AI的点赞总数
     * @param aiId AI角色ID
     * @return 点赞总数
     */
    int getLikeCount(Long aiId);
}