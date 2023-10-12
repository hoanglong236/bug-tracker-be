package com.spring.bugtrackerbe.project.services;

import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.project.dtos.ProjectRequestDTO;
import com.spring.bugtrackerbe.project.dtos.ProjectResponseDTO;
import com.spring.bugtrackerbe.project.entities.Project;
import com.spring.bugtrackerbe.project.mappers.ProjectMapper;
import com.spring.bugtrackerbe.project.repositories.ProjectRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        this.projectService = new ProjectService(this.projectRepository, this.projectMapper);
    }

    private Project setUp_createProject(long id) {
        final Project project = new Project();

        project.setId(id);
        project.setName(RandomStringUtils.randomAlphanumeric(10));
        project.setDeleteFlag(false);

        return project;
    }

    private ProjectResponseDTO setUp_createProjectResponseDTO(long id) {
        final ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();

        projectResponseDTO.setId(id);
        projectResponseDTO.setName(RandomStringUtils.randomAlphanumeric(10));
        projectResponseDTO.setDeleteFlag(false);

        return projectResponseDTO;
    }

    private ProjectRequestDTO setUp_createProjectRequestDTO() {
        final ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName(RandomStringUtils.randomAlphanumeric(10));
        return projectRequestDTO;
    }

    @Test
    void whenGetProjects_thenReturnProjectResponseDTOs() {
        final Project project = this.setUp_createProject(1L);
        final Project project2 = this.setUp_createProject(2L);
        final List<Project> projects = List.of(project, project2);
        final ProjectResponseDTO responseDTO = this.setUp_createProjectResponseDTO(1L);
        final ProjectResponseDTO responseDTO2 = this.setUp_createProjectResponseDTO(2L);

        when(this.projectRepository.findAll()).thenReturn(projects);
        when(this.projectMapper.toResponse(project)).thenReturn(responseDTO);
        when(this.projectMapper.toResponse(project2)).thenReturn(responseDTO2);

        final List<ProjectResponseDTO> actualResponseDTOs = this.projectService.getProjects();

        assertEquals(projects.size(), actualResponseDTOs.size());
        assertTrue(actualResponseDTOs.contains(responseDTO));
        assertTrue(actualResponseDTOs.contains(responseDTO2));
    }

    @Test
    void givenNothing_whenGetProjects_thenReturnEmptyList() {
        when(this.projectRepository.findAll()).thenReturn(Collections.emptyList());
        final List<ProjectResponseDTO> actualResponseDTOs = this.projectService.getProjects();

        assertTrue(actualResponseDTOs.isEmpty());
    }

    @Test
    void whenGetProjectById_thenReturnProjectResponseDTO() {
        final long projectId = 1L;
        final Project project = this.setUp_createProject(projectId);
        final ProjectResponseDTO responseDTO = this.setUp_createProjectResponseDTO(projectId);

        when(this.projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(this.projectMapper.toResponse(project)).thenReturn(responseDTO);

        final ProjectResponseDTO actualResponseDTO =
                this.projectService.getProjectById(projectId);

        assertEquals(responseDTO, actualResponseDTO);
    }

    @Test
    void givenNotFoundProjectId_whenGetProjectById_thenThrowResourcesNotFoundException() {
        final long notFoundProjectId = 1L;
        when(this.projectRepository.findById(notFoundProjectId))
                .thenReturn(Optional.empty());

        assertThrowsExactly(ResourcesNotFoundException.class,
                () -> this.projectService.getProjectById(notFoundProjectId));
    }

    @Test
    void whenCreateProject_thenReturnProjectResponseDTO() {
        final long projectId = 1L;
        final ProjectRequestDTO projectRequestDTO = this.setUp_createProjectRequestDTO();
        final Project project = this.setUp_createProject(projectId);
        final ProjectResponseDTO responseDTO = this.setUp_createProjectResponseDTO(projectId);

        when(this.projectRepository.existsProjectName(projectRequestDTO.getName()))
                .thenReturn(Boolean.FALSE);
        when(this.projectMapper.toProject(projectRequestDTO)).thenReturn(project);
        when(this.projectRepository.save(project)).thenReturn(project);
        when(this.projectMapper.toResponse(project)).thenReturn(responseDTO);

        final ProjectResponseDTO actualResponseDTO =
                this.projectService.createProject(projectRequestDTO);

        assertEquals(responseDTO, actualResponseDTO);
        verify(this.projectRepository).save(project);
    }

    @Test
    void givenExistsName_whenCreateProject_thenThrowResourcesAlreadyExistsException() {
        final ProjectRequestDTO projectRequestDTO = this.setUp_createProjectRequestDTO();
        when(this.projectRepository.existsProjectName(projectRequestDTO.getName()))
                .thenReturn(Boolean.TRUE);

        assertThrowsExactly(ResourcesAlreadyExistsException.class,
                () -> this.projectService.createProject(projectRequestDTO));
        verify(this.projectRepository, never()).save(any());
    }

    @Test
    void whenUpdateProject_thenReturnProjectResponseDTO() {
        final long projectId = 1L;
        final ProjectRequestDTO projectRequestDTO = this.setUp_createProjectRequestDTO();
        final Project project = this.setUp_createProject(projectId);
        final ProjectResponseDTO responseDTO = this.setUp_createProjectResponseDTO(projectId);

        when(this.projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(this.projectRepository.existsProjectName(projectRequestDTO.getName()))
                .thenReturn(Boolean.FALSE);
        when(this.projectRepository.save(project)).thenReturn(project);
        when(this.projectMapper.toResponse(project)).thenReturn(responseDTO);

        final ProjectResponseDTO actualResponseDTO =
                this.projectService.updateProject(projectId, projectRequestDTO);

        assertEquals(responseDTO, actualResponseDTO);
        verify(this.projectRepository).save(project);
    }

    @Test
    void givenNotFoundProjectId_whenUpdateProject_thenThrowResourcesNotFoundException() {
        final long notFoundProjectId = 1L;
        final ProjectRequestDTO projectRequestDTO = this.setUp_createProjectRequestDTO();
        when(this.projectRepository.findById(notFoundProjectId)).thenReturn(Optional.empty());

        assertThrowsExactly(ResourcesNotFoundException.class,
                () -> this.projectService.updateProject(notFoundProjectId, projectRequestDTO));
        verify(this.projectRepository, never()).save(any());
    }

    @Test
    void givenExistsName_whenUpdateProject_thenThrowResourcesAlreadyExistsException() {
        final long projectId = 1L;
        final ProjectRequestDTO projectRequestDTO = this.setUp_createProjectRequestDTO();
        final Project project = this.setUp_createProject(projectId);

        when(this.projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(this.projectRepository.existsProjectName(projectRequestDTO.getName()))
                .thenReturn(Boolean.TRUE);

        assertThrowsExactly(ResourcesAlreadyExistsException.class,
                () -> this.projectService.updateProject(projectId, projectRequestDTO));
        verify(this.projectRepository, never()).save(any());
    }

    @Test
    void whenDeleteProjectById_thenDeleted() {
        final long projectId = 1L;
        final Project project = this.setUp_createProject(projectId);
        when(this.projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        this.projectService.deleteProjectById(projectId);

        final ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(this.projectRepository).save(projectCaptor.capture());

        final Project projectToSave = projectCaptor.getValue();
        assertTrue(projectToSave.getDeleteFlag());
    }

    @Test
    void givenNotFoundProjectId_whenDeleteProjectById_thenThrowResourcesNotFoundException() {
        final long notFoundProjectId = 1L;
        when(this.projectRepository.findById(notFoundProjectId)).thenReturn(Optional.empty());

        assertThrowsExactly(ResourcesNotFoundException.class,
                () -> this.projectService.deleteProjectById(notFoundProjectId));
        verify(this.projectRepository, never()).save(any());
    }
}