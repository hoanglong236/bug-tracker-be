package com.spring.bugtrackerbe.project.dtos;

import jakarta.validation.constraints.NotNull;

public class ProjectMemberRequestDTO {

    @NotNull
    private Integer projectId;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer projectRoleId;

    public ProjectMemberRequestDTO() {
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

    public Integer getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
    }
}
