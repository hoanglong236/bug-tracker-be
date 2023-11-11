package com.spring.bugtrackerbe.dto;

import java.util.List;

public class ProjectDetailsResponseDTO {

    private ProjectResponseDTO project;
    private List<MemberResponseDTO> members;

    public ProjectDetailsResponseDTO() {
    }

    public ProjectDetailsResponseDTO(ProjectResponseDTO project, List<MemberResponseDTO> members) {
        this.project = project;
        this.members = members;
    }

    public ProjectResponseDTO getProject() {
        return project;
    }

    public void setProject(ProjectResponseDTO project) {
        this.project = project;
    }

    public List<MemberResponseDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberResponseDTO> members) {
        this.members = members;
    }
}
