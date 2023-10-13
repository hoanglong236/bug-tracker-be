package com.spring.bugtrackerbe.project.dtos;

import jakarta.validation.constraints.NotNull;

public class ProjectMemberRequestDTO {

    @NotNull
    private Long projectId;
    @NotNull
    private Long userId;
    @NotNull
    private Integer projectRoleId;

    public ProjectMemberRequestDTO() {
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
}
