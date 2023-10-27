package com.spring.bugtrackerbe.project.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dto.ProjectRoleRequestDTO;
import com.spring.bugtrackerbe.project.dto.ProjectRoleResponseDTO;
import com.spring.bugtrackerbe.project.services.ProjectRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/roles")
public class ProjectRoleController {

    private final ProjectRoleService projectRoleService;

    @Autowired
    public ProjectRoleController(ProjectRoleService projectRoleService) {
        this.projectRoleService = projectRoleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectRoleResponseDTO> getProjectRoles() {
        return this.projectRoleService.getProjectRoles();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectRoleResponseDTO createProjectRole(
            @RequestBody @Valid ProjectRoleRequestDTO projectRoleRequestDTO) {

        try {
            return this.projectRoleService.createProjectRole(projectRoleRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectRoleResponseDTO updateProjectRole(
            @PathVariable int id, @RequestBody @Valid ProjectRoleRequestDTO projectRoleRequestDTO
    ) {
        try {
            return this.projectRoleService.updateProjectRole(id, projectRoleRequestDTO);
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
    public void deleteProjectRole(@PathVariable int id) {
        try {
            this.projectRoleService.deleteProjectRoleById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }
}
