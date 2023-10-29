package com.spring.bugtrackerbe.project.mappers;

import com.spring.bugtrackerbe.project.dto.ProjectRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectResponseDTO;
import com.spring.bugtrackerbe.project.entities.Project;
import org.springframework.stereotype.Component;

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
}
