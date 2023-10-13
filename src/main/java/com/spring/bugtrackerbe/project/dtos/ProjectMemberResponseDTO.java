package com.spring.bugtrackerbe.project.dtos;

import java.time.LocalDateTime;

public class ProjectMemberResponseDTO {

    private Long id;
    private Long projectId;
    private Long userId;
    private Integer projectRoleId;
    private Boolean deleteFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProjectMemberResponseDTO() {
    }

    public ProjectMemberResponseDTO(
            Long id,
            Long projectId,
            Long userId,
            Integer projectRoleId,
            Boolean deleteFlag,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.projectRoleId = projectRoleId;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
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
