package com.spring.bugtrackerbe.dto;

import com.spring.bugtrackerbe.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public class ProjectMemberRequestDTO {

    @NotNull
    private Integer projectId;
    @NotNull
    private Integer userId;
    @NotNull
    private ProjectRole role;

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
}
