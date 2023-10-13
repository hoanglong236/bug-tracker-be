package com.spring.bugtrackerbe.project.mappers;

import com.spring.bugtrackerbe.project.dtos.ProjectMemberRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectMemberResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {

    public ProjectMember toProjectMember(ProjectMemberRequestDTO memberRequestDTO) {
        final ProjectMember projectMember = new ProjectMember();

        projectMember.setProjectId(memberRequestDTO.getProjectId());
        projectMember.setUserId(memberRequestDTO.getUserId());
        projectMember.setProjectRoleId(memberRequestDTO.getProjectRoleId());

        return projectMember;
    }

    public ProjectMemberResponseDTO toResponse(ProjectMember projectMember) {
        return new ProjectMemberResponseDTO(
                projectMember.getId(),
                projectMember.getProjectId(),
                projectMember.getUserId(),
                projectMember.getProjectRoleId(),
                projectMember.getDeleteFlag(),
                projectMember.getCreatedAt(),
                projectMember.getUpdatedAt()
        );
    }
}