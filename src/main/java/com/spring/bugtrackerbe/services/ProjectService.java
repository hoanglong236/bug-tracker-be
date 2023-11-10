package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.*;
import com.spring.bugtrackerbe.entities.Project;
import com.spring.bugtrackerbe.entities.ProjectMember;
import com.spring.bugtrackerbe.enums.ProjectRole;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.mappers.ProjectMapper;
import com.spring.bugtrackerbe.messages.ProjectMessage;
import com.spring.bugtrackerbe.repositories.ProjectMemberRepository;
import com.spring.bugtrackerbe.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(
            ProjectRepository projectRepository,
            ProjectMemberRepository projectMemberRepository,
            ProjectMapper projectMapper
    ) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
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

    public ProjectResponseDTO getProjectById(int id) {
        final Project project = this.projectRepository.findById(id)
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
        final Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));

        if (!Objects.equals(project.getName(), projectRequestDTO.getName())) {
            if (this.projectRepository.existsProjectName(projectRequestDTO.getName())) {
                throw new ResourcesAlreadyExistsException(ProjectMessage.NAME_ALREADY_EXISTS);
            }
        }
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

    public ProjectDetailsResponseDTO getProjectDetailsById(int id) {
        final Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));
        final List<ProjectMember> members = this.projectMemberRepository.findByProjectId(id);
        return this.projectMapper.toProjectDetailsResponseDTO(project, members);
    }

    public ProjectMemberResponseDTO changeMemberRole(int memberId, ProjectRole role) {
        final ProjectMember projectMember = this.projectMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));
        projectMember.setRole(role);
        projectMember.setUpdatedAt(LocalDateTime.now());

        final ProjectMember savedMember = this.projectMemberRepository.save(projectMember);
        return this.projectMapper.toProjectMemberResponse(savedMember);
    }

    public ProjectMemberResponseDTO addMember(ProjectMemberRequestDTO memberRequestDTO) {
        if (this.projectRepository.findById(memberRequestDTO.getProjectId()).isEmpty()) {
            throw new ResourcesNotFoundException(ProjectMessage.PROJECT_ID_INVALID);
        }
        // TODO: check user id exist in users table
        //  and user id not exist in project_members table

        final ProjectMember member = this.projectMapper.toProjectMember(memberRequestDTO);
        return this.projectMapper.toProjectMemberResponse(
                this.projectMemberRepository.save(member));
    }

    public void removeMember(int memberId) {
        final ProjectMember member = this.projectMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));
        member.setDeleteFlag(true);
        member.setUpdatedAt(LocalDateTime.now());

        this.projectMemberRepository.save(member);
    }
}
