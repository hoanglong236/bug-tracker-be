package com.spring.bugtrackerbe.project.dtos;

import java.time.LocalDateTime;

public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String note;
    private Boolean closeFlag;
    private Boolean deleteFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProjectResponseDTO() {
    }

    public ProjectResponseDTO(
            Long id,
            String name,
            String note,
            Boolean closeFlag,
            Boolean deleteFlag,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.closeFlag = closeFlag;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(Boolean closeFlag) {
        this.closeFlag = closeFlag;
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
