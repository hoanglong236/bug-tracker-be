package com.spring.bugtrackerbe.project.dto;

import com.spring.bugtrackerbe.project.enums.ProjectRole;

import java.time.LocalDateTime;

public class ProjectMemberResponseDTO {

    private Integer id;
    private Integer projectId;
    private Integer userId;
    private ProjectRole role;
    private Boolean deleteFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProjectMemberResponseDTO() {
    }

    public ProjectMemberResponseDTO(
            Integer id,
            Integer projectId,
            Integer userId,
            ProjectRole role,
            Boolean deleteFlag,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
        this.deleteFlag = deleteFlag;
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

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
