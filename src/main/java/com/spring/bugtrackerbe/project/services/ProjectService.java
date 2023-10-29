package com.spring.bugtrackerbe.project.services;

import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dto.FilterProjectsRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectResponseDTO;
import com.spring.bugtrackerbe.project.entities.Project;
import com.spring.bugtrackerbe.project.mappers.ProjectMapper;
import com.spring.bugtrackerbe.project.messages.ProjectMessage;
import com.spring.bugtrackerbe.project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public Page<ProjectResponseDTO> filterProjects(
            FilterProjectsRequestDTO filterProjectsRequestDTO
    ) {
        final Pageable pageable = PageRequest.of(
                filterProjectsRequestDTO.getPageNumber(),
                filterProjectsRequestDTO.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return this.projectRepository.filterProjects(pageable)
                .map(this.projectMapper::toResponse);
    }

    public ProjectResponseDTO getProjectById(int projectId) {
        final Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));
        return this.projectMapper.toResponse(project);
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        final String projectName = projectRequestDTO.getName();
        if (this.projectRepository.existsProjectName(projectName)) {
            throw new ResourcesAlreadyExistsException(ProjectMessage.NAME_ALREADY_EXISTS);
        }

        final Project savedProject = this.projectRepository
                .save(this.projectMapper.toProject(projectRequestDTO));
        return this.projectMapper.toResponse(savedProject);
    }

    public ProjectResponseDTO updateProject(int id, ProjectRequestDTO projectRequestDTO) {
        if (this.projectRepository.existsProjectName(projectRequestDTO.getName())) {
            throw new ResourcesAlreadyExistsException(ProjectMessage.NAME_ALREADY_EXISTS);
        }

        final Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));
        project.setName(projectRequestDTO.getName());
        project.setNote(projectRequestDTO.getNote());
        project.setCloseFlag(projectRequestDTO.getCloseFlag());
        project.setUpdatedAt(LocalDateTime.now());

        final Project updatedProject = this.projectRepository.save(project);
        return this.projectMapper.toResponse(updatedProject);
    }

    public void deleteProjectById(int id) {
        final Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));
        project.setDeleteFlag(true);
        project.setUpdatedAt(LocalDateTime.now());

        this.projectRepository.save(project);
    }
}
