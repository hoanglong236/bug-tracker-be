package com.spring.bugtrackerbe.post.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @SequenceGenerator(name = "posts_seq", sequenceName = "posts_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_seq")
    private Integer id;
    private Integer reporterId;
    private Integer assignerId;
    private Integer projectId;
    @Enumerated(EnumType.STRING)
    private PostPhase phase;
    @Enumerated(EnumType.STRING)
    private PostStatus status;
    private String title;
    private String bugDesc;
    private String bugReason = "";
    private String bugFixMethod = "";
    private Boolean deleteFlag = Boolean.FALSE;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public Integer getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(Integer assignerId) {
        this.assignerId = assignerId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public PostPhase getPhase() {
        return phase;
    }

    public void setPhase(PostPhase phase) {
        this.phase = phase;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBugDesc() {
        return bugDesc;
    }

    public void setBugDesc(String bugDesc) {
        this.bugDesc = bugDesc;
    }

    public String getBugReason() {
        return bugReason;
    }

    public void setBugReason(String bugReason) {
        this.bugReason = bugReason;
    }

    public String getBugFixMethod() {
        return bugFixMethod;
    }

    public void setBugFixMethod(String bugFixMethod) {
        this.bugFixMethod = bugFixMethod;
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
