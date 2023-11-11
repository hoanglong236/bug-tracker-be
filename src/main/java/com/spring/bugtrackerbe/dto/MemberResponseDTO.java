package com.spring.bugtrackerbe.dto;

import com.spring.bugtrackerbe.enums.ProjectRole;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    private Integer id;
    private Integer projectId;
    private Integer userId;
    private String email;
    private String name;
    private ProjectRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberResponseDTO() {
    }

    public MemberResponseDTO(
            Integer id,
            Integer projectId,
            Integer userId,
            String email,
            String name,
            ProjectRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
