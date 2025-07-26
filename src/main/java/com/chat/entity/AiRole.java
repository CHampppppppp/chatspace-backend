package com.chat.entity;

import java.sql.Timestamp;

public class AiRole {
    private Long aiId;
    private String name;
    private String description;
    private String prompt;
    private String avatar;
    private String createdBy;
    private Timestamp createdAt;
    private Long likes;
    private String status;

    // 构造函数
    public AiRole() {
    }

    public AiRole(Long aiId, String name, String description, String prompt, String avatar, String createdBy,
            Timestamp createdAt, Long likes, String status) {
        this.aiId = aiId;
        this.name = name;
        this.description = description;
        this.prompt = prompt;
        this.avatar = avatar;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.likes = likes;
        this.status = status;
    }

    // Getter 和 Setter 方法
    public Long getAiId() {
        return aiId;
    }

    public void setAiId(Long aiId) {
        this.aiId = aiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return " AiRole { " +
                "aiId=" + aiId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prompt='" + prompt + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", likes=" + likes +
                ", status='" + status + '\'' +
                '}';
    }
}