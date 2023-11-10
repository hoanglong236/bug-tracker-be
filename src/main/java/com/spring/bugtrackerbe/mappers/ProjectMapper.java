package com.spring.bugtrackerbe.mappers;

import com.spring.bugtrackerbe.dto.*;
import com.spring.bugtrackerbe.entities.Project;
import com.spring.bugtrackerbe.entities.ProjectMember;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {

    public Project toProject(ProjectRequestDTO projectRequestDTO) {
        final Project project = new Project();

        project.setName(projectRequestDTO.getName());
        project.setKind(projectRequestDTO.getKind());
        project.setArchitecture(projectRequestDTO.getArchitecture());
        project.setTechnology(projectRequestDTO.getTechnology());
        project.setLang(projectRequestDTO.getLang());
        project.setDb(projectRequestDTO.getDb());
        project.setNote(projectRequestDTO.getNote());
        project.setCloseFlag(projectRequestDTO.getCloseFlag());

        return project;
    }

    public ProjectResponseDTO toResponse(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getKind(),
                project.getArchitecture(),
                project.getTechnology(),
                project.getLang(),
                project.getDb(),
                project.getNote(),
                project.getCloseFlag(),
                project.getDeleteFlag(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    public ProjectMember toProjectMember(ProjectMemberRequestDTO memberRequestDTO) {
        final ProjectMember projectMember = new ProjectMember();

        projectMember.setProjectId(memberRequestDTO.getProjectId());
        projectMember.setUserId(memberRequestDTO.getUserId());
        projectMember.setRole(memberRequestDTO.getRole());

        return projectMember;
    }

    public ProjectMemberResponseDTO toProjectMemberResponse(ProjectMember projectMember) {
        return new ProjectMemberResponseDTO(
                projectMember.getId(),
                projectMember.getProjectId(),
                projectMember.getUserId(),
                projectMember.getRole(),
                projectMember.getDeleteFlag(),
                projectMember.getCreatedAt(),
                projectMember.getUpdatedAt()
        );
    }

    public ProjectDetailsResponseDTO toProjectDetailsResponseDTO(
            Project project, List<ProjectMember> members
    ) {
        return new ProjectDetailsResponseDTO(
                this.toResponse(project), members.stream().map(this::toProjectMemberResponse).toList());
    }
}
