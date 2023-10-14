package com.spring.bugtrackerbe.project.repositories;

import com.spring.bugtrackerbe.project.entities.Project;
import org.apache.commons.lang3.RandomStringUtils;
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

    private Project setUp_createProject() {
        return this.setUp_createProject(false);
    }

    private Project setUp_createDeletedProject() {
        return this.setUp_createProject(true);
    }

    private Project setUp_createProject(boolean deleteFlag) {
        final Project project = new Project();

        project.setName(RandomStringUtils.randomAlphanumeric(10));
        project.setDeleteFlag(deleteFlag);

        return this.testEntityManager.persistAndFlush(project);
    }

    @Test
    void whenFindAll_thenReturnProjects() {
        this.setUp_createProject();
        this.setUp_createProject();
        final List<Project> actualProjects = this.projectRepository.findAll();

        assertEquals(2, actualProjects.size());
    }

    @Test
    void givenNothing_whenFindAll_thenReturnEmptyList() {
        final List<Project> actualProjects = this.projectRepository.findAll();
        assertTrue(actualProjects.isEmpty());
    }

    @Test
    void givenDeletedProjects_whenFindAll_thenReturnEmptyList() {
        this.setUp_createDeletedProject();
        final List<Project> actualProjects = this.projectRepository.findAll();
        assertTrue(actualProjects.isEmpty());
    }

    @Test
    void whenFindById_thenReturnProjectOptional() {
        final Project project = this.setUp_createProject();
        final Optional<Project> actualProjectOptional =
                this.projectRepository.findById(project.getId());

        assertFalse(actualProjectOptional.isEmpty());
    }

    @Test
    void givenNothing_whenFindById_thenReturnEmptyOptional() {
        final Optional<Project> actualProjectOptional = this.projectRepository.findById(1);
        assertTrue(actualProjectOptional.isEmpty());
    }

    @Test
    void givenDeletedProjects_whenFindById_thenReturnEmptyOptional() {
        final Project deletedProject = this.setUp_createDeletedProject();
        final Optional<Project> actualProjectOptional =
                this.projectRepository.findById(deletedProject.getId());
        assertTrue(actualProjectOptional.isEmpty());
    }

    @Test
    void whenExistsProjectName_thenReturnTrue() {
        final Project project = this.setUp_createProject();
        assertTrue(this.projectRepository.existsProjectName(project.getName()));
    }

    @Test
    void givenNothing_whenExistsProjectName_thenReturnFalse() {
        assertFalse(this.projectRepository.existsProjectName("Test project"));
    }

    @Test
    void givenDeletedProjects_whenExistsProjectName_thenReturnFalse() {
        final Project deletedProject = this.setUp_createDeletedProject();
        assertFalse(this.projectRepository.existsProjectName(deletedProject.getName()));
    }
}