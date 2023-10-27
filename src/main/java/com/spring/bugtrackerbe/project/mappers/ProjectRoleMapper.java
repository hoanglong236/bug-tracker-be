package com.spring.bugtrackerbe.project.mappers;

import com.spring.bugtrackerbe.project.dto.ProjectRoleRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectRoleResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectRole;
import org.springframework.stereotype.Component;

@Component
public class ProjectRoleMapper {

    public ProjectRole toProjectRole(ProjectRoleRequestDTO projectRoleRequestDTO) {
        final ProjectRole projectRole = new ProjectRole();

        projectRole.setName(projectRoleRequestDTO.getName());
        projectRole.setNote(projectRoleRequestDTO.getNote());

        return projectRole;
    }

    public ProjectRoleResponseDTO toResponse(ProjectRole projectRole) {
        return new ProjectRoleResponseDTO(
                projectRole.getId(),
                projectRole.getName(),
                projectRole.getNote(),
                projectRole.getDeleteFlag(),
                projectRole.getCreatedAt(),
                projectRole.getUpdatedAt()
        );
    }
}
