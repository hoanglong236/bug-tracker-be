package com.spring.bugtrackerbe.project.services;

import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dto.ProjectMemberRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectMemberResponseDTO;
import com.spring.bugtrackerbe.project.entities.ProjectMember;
import com.spring.bugtrackerbe.project.enums.ProjectRole;
import com.spring.bugtrackerbe.project.mappers.ProjectMemberMapper;
import com.spring.bugtrackerbe.project.messages.ProjectMemberMessage;
import com.spring.bugtrackerbe.project.repositories.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Autowired
    public ProjectMemberService(
            ProjectMemberRepository projectMemberRepository,
            ProjectMemberMapper projectMemberMapper
    ) {
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
        final ProjectMember savedProjectMember = this.projectMemberRepository
                .save(this.projectMemberMapper.toProjectMember(memberRequestDTO));
        return this.projectMemberMapper.toResponse(savedProjectMember);
    }

    public ProjectMemberResponseDTO changeMemberRole(int memberId, ProjectRole role) {
        final ProjectMember member = this.projectMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMemberMessage.NOT_FOUND));
        member.setRole(role);
        member.setUpdatedAt(LocalDateTime.now());

        final ProjectMember savedMember = this.projectMemberRepository.save(member);
        return this.projectMemberMapper.toResponse(savedMember);
    }

    public void deleteMemberById(int memberId) {
        final ProjectMember projectMember = this.projectMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMemberMessage.NOT_FOUND));
        projectMember.setDeleteFlag(true);
        projectMember.setUpdatedAt(LocalDateTime.now());

        this.projectMemberRepository.save(projectMember);
    }
}
