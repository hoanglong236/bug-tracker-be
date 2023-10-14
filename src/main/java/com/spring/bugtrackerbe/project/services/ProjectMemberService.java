package com.spring.bugtrackerbe.project.services;

import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dtos.ProjectMemberRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectMemberResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectMember;
import com.spring.bugtrackerbe.project.mappers.ProjectMemberMapper;
import com.spring.bugtrackerbe.project.messages.ProjectMemberMessage;
import com.spring.bugtrackerbe.project.repositories.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectMemberMapper projectMemberMapper;

    @Autowired
    public ProjectMemberService(ProjectMemberRepository projectMemberRepository, ProjectMemberMapper projectMemberMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    public List<ProjectMemberResponseDTO> getMembersByProjectId(int projectId) {
        final List<ProjectMember> projectMembers =
                this.projectMemberRepository.findByProjectId(projectId);
        return projectMembers.stream()
                .map(this.projectMemberMapper::toResponse)
                .toList();
    }

    public ProjectMemberResponseDTO createMember(ProjectMemberRequestDTO memberRequestDTO) {
        // TODO: check user id
        // TODO: check project id
        // TODO: check role id
        final ProjectMember savedProjectMember = this.projectMemberRepository
                .save(this.projectMemberMapper.toProjectMember(memberRequestDTO));
        return this.projectMemberMapper.toResponse(savedProjectMember);
    }

    private boolean isRoleIdValid(int roleId) {
        return true;
    }

    public ProjectMemberResponseDTO changeMemberRole(int memberId, int roleId) {
        final Optional<ProjectMember> memberOptional =
                this.projectMemberRepository.findById(memberId);
        if (memberOptional.isEmpty()) {
            throw new ResourcesNotFoundException(ProjectMemberMessage.NOT_FOUND);
        }
        if (!isRoleIdValid(roleId)) {
            throw new ResourcesNotFoundException(ProjectMemberMessage.PROJECT_ID_INVALID);
        }

        final ProjectMember member = memberOptional.get();
        member.setProjectRoleId(roleId);
        member.setUpdatedAt(LocalDateTime.now());

        final ProjectMember savedMember = this.projectMemberRepository.save(member);
        return this.projectMemberMapper.toResponse(savedMember);
    }

    public void deleteMemberById(int memberId) {
        final Optional<ProjectMember> projectMemberOptional = this.projectMemberRepository
                .findById(memberId);
        if (projectMemberOptional.isEmpty()) {
            throw new ResourcesNotFoundException(ProjectMemberMessage.NOT_FOUND);
        }

        final ProjectMember projectMember = projectMemberOptional.get();
        projectMember.setDeleteFlag(true);
        projectMember.setUpdatedAt(LocalDateTime.now());

        this.projectMemberRepository.save(projectMember);
    }
}
