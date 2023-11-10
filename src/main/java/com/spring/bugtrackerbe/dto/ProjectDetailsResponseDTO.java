package com.spring.bugtrackerbe.dto;

import java.util.List;

public class ProjectDetailsResponseDTO {

    private ProjectResponseDTO project;
    private List<ProjectMemberResponseDTO> members;

    public ProjectDetailsResponseDTO() {
    }

    public ProjectDetailsResponseDTO(ProjectResponseDTO project, List<ProjectMemberResponseDTO> members) {
        this.project = project;
        this.members = members;
    }

    public ProjectResponseDTO getProject() {
        return project;
    }

    public void setProject(ProjectResponseDTO project) {
        this.project = project;
    }

    public List<ProjectMemberResponseDTO> getMembers() {
        return members;
    }

    public void setMembers(List<ProjectMemberResponseDTO> members) {
        this.members = members;
    }
}
