package com.spring.bugtrackerbe.project.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dtos.ProjectRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectResponseDTO;
import com.spring.bugtrackerbe.project.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectResponseDTO> getAll() {
        return this.projectService.getProjects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDTO getProjectById(@PathVariable long id) {
        try {
            return this.projectService.getProjectById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO createProject(
            @RequestBody @Valid ProjectRequestDTO projectRequestDTO) {

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
            @PathVariable long id, @RequestBody @Valid ProjectRequestDTO projectRequestDTO
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
    public void deleteProject(@PathVariable long id) {
        try {
            this.projectService.deleteProjectById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }
}
