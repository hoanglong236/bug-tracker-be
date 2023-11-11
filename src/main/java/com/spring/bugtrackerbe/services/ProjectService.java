package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.*;
import com.spring.bugtrackerbe.entities.Member;
import com.spring.bugtrackerbe.entities.Project;
import com.spring.bugtrackerbe.entities.User;
import com.spring.bugtrackerbe.enums.ProjectRole;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.messages.ProjectMessage;
import com.spring.bugtrackerbe.repositories.ICustomMemberRepository;
import com.spring.bugtrackerbe.repositories.MemberRepository;
import com.spring.bugtrackerbe.repositories.ProjectRepository;
import com.spring.bugtrackerbe.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ICustomMemberRepository customMemberRepository;

    @Autowired
    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            MemberRepository memberRepository,
            ICustomMemberRepository customMemberRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.customMemberRepository = customMemberRepository;
    }

    private static Project toProject(ProjectRequestDTO projectRequestDTO) {
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

    private static ProjectResponseDTO toProjectResponseDTO(Project project) {
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
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    private static MemberResponseDTO toMemberResponseDTO(
            Member member, User memberUser
    ) {
        return new MemberResponseDTO(
                member.getId(),
                member.getProjectId(),
                member.getUserId(),
                memberUser.getEmail(),
                memberUser.getName(),
                member.getRole(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
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
                .map(ProjectService::toProjectResponseDTO);
    }

    public ProjectResponseDTO getProjectById(int id) {
        final Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.NOT_FOUND));
        return toProjectResponseDTO(project);
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        final String projectName = projectRequestDTO.getName();
        if (this.projectRepository.existsProjectName(projectName)) {
            throw new ResourcesAlreadyExistsException(ProjectMessage.NAME_ALREADY_EXISTS);
        }

        final Project project = toProject(projectRequestDTO);
        return toProjectResponseDTO(this.projectRepository.save(project));
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

        return toProjectResponseDTO(this.projectRepository.save(project));
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
        final List<MemberResponseDTO> memberResponseDTOs = this.customMemberRepository
                .findMemberResponseDTOByProjectId(id);

        return new ProjectDetailsResponseDTO(toProjectResponseDTO(project), memberResponseDTOs);
    }

    public MemberResponseDTO addMember(int projectId, String memberEmail) {
        if (!this.projectRepository.existsById(projectId)) {
            throw new ResourcesNotFoundException(ProjectMessage.NOT_FOUND);
        }
        final User user = this.userRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));

        final Member member = new Member();
        member.setProjectId(projectId);
        member.setUserId(user.getId());
        member.setRole(ProjectRole.MEMBER);

        final Member savedMember = this.memberRepository.save(member);
        return toMemberResponseDTO(savedMember, user);
    }

    public MemberResponseDTO changeMemberRole(int memberId, ProjectRole role) {
        final Member member = this.memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));
        member.setRole(role);
        member.setUpdatedAt(LocalDateTime.now());

        final Member savedMember = this.memberRepository.save(member);
        final User user = this.userRepository.findById(savedMember.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));

        return toMemberResponseDTO(savedMember, user);
    }

    public void removeMember(int memberId) {
        final Member member = this.memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourcesNotFoundException(ProjectMessage.MEMBER_NOT_FOUND));
        member.setDeleteFlag(true);
        member.setUpdatedAt(LocalDateTime.now());

        this.memberRepository.save(member);
    }
}
