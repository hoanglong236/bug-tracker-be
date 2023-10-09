package com.spring.bugtrackerbe.project.mappers;

import com.spring.bugtrackerbe.project.dtos.ProjectRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectResponseDTO;
import com.spring.bugtrackerbe.project.entities.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toProject(ProjectRequestDTO projectRequestDTO) {
        final Project project = new Project();

        project.setName(projectRequestDTO.getName());
        project.setNote(projectRequestDTO.getNote());
        project.setCloseFlag(projectRequestDTO.getCloseFlag());

        return project;
    }

    public ProjectResponseDTO toResponse(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getNote(),
                project.getCloseFlag(),
                project.getDeleteFlag(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
