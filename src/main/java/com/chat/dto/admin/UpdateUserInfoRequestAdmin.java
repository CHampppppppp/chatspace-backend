package com.chat.dto.admin;

public class UpdateUserInfoRequestAdmin {
    private Long userId;
    private String username;
    private String email;
    private String avatar;
    private String role;
    private String status;
    private int isBlocked;

    // 无参构造函数
    public UpdateUserInfoRequestAdmin() {}

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public int getIsBlocked() {return isBlocked;}

    public void setIsBlocked(int isBlocked) {this.isBlocked = isBlocked;}
}
