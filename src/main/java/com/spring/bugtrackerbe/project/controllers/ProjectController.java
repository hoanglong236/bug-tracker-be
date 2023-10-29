package com.spring.bugtrackerbe.project.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dto.*;
import com.spring.bugtrackerbe.project.enums.ProjectRole;
import com.spring.bugtrackerbe.project.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectResponseDTO> filterProjects(
            @RequestBody @Valid FilterProjectsRequestDTO filterProjectsRequestDTO
    ) {
        return this.projectService.filterProjects(filterProjectsRequestDTO);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO createProject(
            @RequestBody @Valid ProjectRequestDTO projectRequestDTO
    ) {
        try {
            return this.projectService.createProject(projectRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDTO updateProject(
            @PathVariable int id, @RequestBody @Valid ProjectRequestDTO projectRequestDTO
    ) {
        try {
            return this.projectService.updateProject(id, projectRequestDTO);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.UPDATE_FAILED + e.getMessage());
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.UPDATE_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable int id) {
        try {
            this.projectService.deleteProjectById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectInfoResponseDTO getProjectInfo(@PathVariable int id) {
        try {
            return this.projectService.getProjectInfo(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/change-member-role/{memberId}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectMemberResponseDTO changeMemberRole(
            @PathVariable Integer memberId, @PathVariable ProjectRole role
    ) {
        try {
            return this.projectService.changeMemberRole(memberId, role);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/add-member")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberResponseDTO addMember(
            @RequestBody @Valid ProjectMemberRequestDTO memberRequestDTO
    ) {
        try {
            return this.projectService.addMember(memberRequestDTO);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/remove-member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@PathVariable Integer memberId) {
        try {
            this.projectService.removeMember(memberId);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
