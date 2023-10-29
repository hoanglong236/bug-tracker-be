package com.spring.bugtrackerbe.project.entities;

import com.spring.bugtrackerbe.project.enums.ProjectRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_members")
public class ProjectMember {

    @Id
    @SequenceGenerator(
            name = "project_members_seq",
            sequenceName = "project_members_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_members_seq")
    private Integer id;
    private Integer projectId;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private ProjectRole role;
    private Boolean deleteFlag = Boolean.FALSE;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

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
