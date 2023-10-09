package com.spring.bugtrackerbe.project.repositories;

import com.spring.bugtrackerbe.project.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private Project createSetupProject(String projectName, boolean deleteFlag) {
        final Project project = new Project();

        project.setName(projectName);
        project.setDeleteFlag(deleteFlag);

        return this.testEntityManager.persistAndFlush(project);
    }

    private Project createSetupProject(String projectName) {
        return createSetupProject(projectName, false);
    }

    private Project createSetupDeletedProject(String projectName) {
        return createSetupProject(projectName, true);
    }

    @Test
    void whenFindAll_thenReturnProjects() {
        final Project project = this.createSetupProject("Test project");
        final List<Project> projects = this.projectRepository.findAll();

        assertEquals(1, projects.size());
        assertEquals(project.getId(), projects.get(0).getId());
    }

    @Test
    void givenNothing_whenFindAll_thenReturnEmptyList() {
        final List<Project> projects = this.projectRepository.findAll();
        assertEquals(0, projects.size());
    }

    @Test
    void givenDeletedProjects_whenFindAll_thenReturnEmptyList() {
        this.createSetupDeletedProject("Deleted project");
        final List<Project> projects = this.projectRepository.findAll();
        assertEquals(0, projects.size());
    }

    @Test
    void whenFindById_thenReturnProjectOptional() {
        final Project project = this.createSetupProject("Test project");
        final Optional<Project> projectOptional = this.projectRepository.findById(project.getId());

        assertFalse(projectOptional.isEmpty());
        assertEquals(project.getName(), projectOptional.get().getName());
    }

    @Test
    void givenNothing_whenFindById_thenReturnEmptyOptional() {
        final Optional<Project> projectOptional = this.projectRepository.findById(1L);
        assertTrue(projectOptional.isEmpty());
    }

    @Test
    void givenDeletedProjects_whenFindById_thenReturnEmptyOptional() {
        final Project deletedProject = this.createSetupDeletedProject("Deleted project");
        final Optional<Project> projectOptional = this.projectRepository.findById(deletedProject.getId());
        assertTrue(projectOptional.isEmpty());
    }

    @Test
    void whenExistsProjectName_thenReturnTrue() {
        final Project project = this.createSetupProject("Test project");
        assertTrue(this.projectRepository.existsProjectName(project.getName()));
    }

    @Test
    void givenNothing_whenExistsProjectName_thenReturnFalse() {
        assertFalse(this.projectRepository.existsProjectName("Test project"));
    }

    @Test
    void givenDeletedProjects_whenExistsProjectName_thenReturnFalse() {
        final Project deletedProject = this.createSetupDeletedProject("Deleted project");
        assertFalse(this.projectRepository.existsProjectName(deletedProject.getName()));
    }
}