package com.chat.entity;

import java.sql.Timestamp;

/**
 * AI点赞实体类
 * 对应数据库表 ai_likes
 */
public class AiLike {
    private Long id;
    private Long userId;
    private Long aiId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 构造函数
    public AiLike() {}

    public AiLike(Long id, Long userId, Long aiId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.aiId = aiId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAiId() {
        return aiId;
    }

    public void setAiId(Long aiId) {
        this.aiId = aiId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AiLike{" +
                "id=" + id +
                ", userId=" + userId +
                ", aiId=" + aiId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}