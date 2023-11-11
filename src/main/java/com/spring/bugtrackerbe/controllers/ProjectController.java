package com.spring.bugtrackerbe.controllers;

import com.spring.bugtrackerbe.dto.*;
import com.spring.bugtrackerbe.enums.ProjectRole;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.messages.CommonMessage;
import com.spring.bugtrackerbe.services.ProjectService;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDTO getProject(@PathVariable int id) {
        try {
            return this.projectService.getProjectById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable int id) {
        try {
            this.projectService.deleteProjectById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDetailsResponseDTO getProjectDetails(@PathVariable int id) {
        try {
            return this.projectService.getProjectDetailsById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/details/{id}/add-member/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDTO addMember(
            @PathVariable int id, @PathVariable String email
    ) {
        try {
            return this.projectService.addMember(id, email);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/change-member-role/{memberId}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponseDTO changeMemberRole(
            @PathVariable int memberId, @PathVariable ProjectRole role
    ) {
        try {
            return this.projectService.changeMemberRole(memberId, role);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/remove-member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@PathVariable int memberId) {
        try {
            this.projectService.removeMember(memberId);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
