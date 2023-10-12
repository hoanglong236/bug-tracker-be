package com.spring.bugtrackerbe.project.services;

import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dtos.ProjectRoleRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectRoleResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectRole;
import com.spring.bugtrackerbe.project.mappers.ProjectRoleMapper;
import com.spring.bugtrackerbe.project.messages.ProjectRoleMessage;
import com.spring.bugtrackerbe.project.repositories.ProjectRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectRoleService {

    private final ProjectRoleRepository projectRoleRepository;

    private final ProjectRoleMapper projectRoleMapper;

    @Autowired
    public ProjectRoleService(ProjectRoleRepository projectRoleRepository, ProjectRoleMapper projectRoleMapper) {
        this.projectRoleRepository = projectRoleRepository;
        this.projectRoleMapper = projectRoleMapper;
    }

    public List<ProjectRoleResponseDTO> getProjectRoles() {
        final List<ProjectRole> projectRoles = this.projectRoleRepository.findAll();
        return projectRoles.stream()
                .map(this.projectRoleMapper::toResponse)
                .toList();
    }

    public ProjectRoleResponseDTO createProjectRole(ProjectRoleRequestDTO projectRoleRequestDTO) {
        final String roleName = projectRoleRequestDTO.getName();
        if (this.projectRoleRepository.existsProjectRoleName(roleName)) {
            throw new ResourcesAlreadyExistsException(ProjectRoleMessage.NAME_ALREADY_EXISTS);
        }

        final ProjectRole savedProjectRole = this.projectRoleRepository
                .save(this.projectRoleMapper.toProjectRole(projectRoleRequestDTO));
        return this.projectRoleMapper.toResponse(savedProjectRole);
    }

    public ProjectRoleResponseDTO updateProjectRole(
            int id, ProjectRoleRequestDTO projectRoleRequestDTO) {

        final Optional<ProjectRole> projectRoleOptional = this.projectRoleRepository.findById(id);
        if (projectRoleOptional.isEmpty()) {
            throw new ResourcesNotFoundException(ProjectRoleMessage.NOT_FOUND);
        }
        if (this.projectRoleRepository.existsProjectRoleName(
                projectRoleRequestDTO.getName())) {
            throw new ResourcesAlreadyExistsException(ProjectRoleMessage.NAME_ALREADY_EXISTS);
        }

        final ProjectRole projectRole = projectRoleOptional.get();
        projectRole.setName(projectRoleRequestDTO.getName());
        projectRole.setNote(projectRoleRequestDTO.getNote());
        projectRole.setUpdatedAt(LocalDateTime.now());

        final ProjectRole updatedProjectRole = this.projectRoleRepository.save(projectRole);
        return this.projectRoleMapper.toResponse(updatedProjectRole);
    }

    public void deleteProjectRoleById(int id) {
        final Optional<ProjectRole> projectRoleOptional = this.projectRoleRepository.findById(id);
        if (projectRoleOptional.isEmpty()) {
            throw new ResourcesNotFoundException(ProjectRoleMessage.NOT_FOUND);
        }

        final ProjectRole projectRole = projectRoleOptional.get();
        projectRole.setDeleteFlag(true);
        projectRole.setUpdatedAt(LocalDateTime.now());
        this.projectRoleRepository.save(projectRole);
    }
}
