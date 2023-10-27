package com.spring.bugtrackerbe.project.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dto.ProjectMemberRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectMemberResponseDTO;
import com.spring.bugtrackerbe.project.services.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping("/list-by-project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectMemberResponseDTO> listByProjectId(@PathVariable int projectId) {
        return this.projectMemberService.getMembersByProjectId(projectId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberResponseDTO createMember(
            @RequestBody @Valid ProjectMemberRequestDTO memberRequestDTO) {

        try {
            return this.projectMemberService.createMember(memberRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/change-role/{memberId}/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectMemberResponseDTO changeMemberRole(
            @PathVariable Integer memberId, @PathVariable Integer roleId) {

        try {
            return this.projectMemberService.changeMemberRole(memberId, roleId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@PathVariable Integer memberId) {
        try {
            this.projectMemberService.deleteMemberById(memberId);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }
}
