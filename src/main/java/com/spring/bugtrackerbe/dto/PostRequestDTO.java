package com.spring.bugtrackerbe.dto;

import com.spring.bugtrackerbe.enums.PostPhase;
import com.spring.bugtrackerbe.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostRequestDTO {

    @NotNull
    private Integer reporterId;
    @NotNull
    private Integer assignerId;
    @NotNull
    private Integer projectId;
    @NotNull
    private PostPhase phase;
    @NotNull
    private PostStatus status;
    @NotBlank
    private String title;
    @NotBlank
    private String bugDesc;
    private String bugReason;
    private String bugFixMethod;

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
}
