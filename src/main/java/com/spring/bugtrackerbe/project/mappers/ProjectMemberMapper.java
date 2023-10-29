package com.spring.bugtrackerbe.project.mappers;

import com.spring.bugtrackerbe.project.dto.ProjectMemberRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectMemberResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {

    public ProjectMember toProjectMember(ProjectMemberRequestDTO memberRequestDTO) {
        final ProjectMember projectMember = new ProjectMember();

        projectMember.setProjectId(memberRequestDTO.getProjectId());
        projectMember.setUserId(memberRequestDTO.getUserId());
        projectMember.setRole(memberRequestDTO.getRole());

        return projectMember;
    }

    public ProjectMemberResponseDTO toResponse(ProjectMember projectMember) {
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
}
