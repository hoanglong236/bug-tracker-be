package com.spring.bugtrackerbe.project.repositories;

import com.spring.bugtrackerbe.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT p FROM Project p WHERE p.deleteFlag = FALSE")
    List<Project> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT p FROM Project p WHERE p.deleteFlag = FALSE AND p.id = ?1")
    Optional<Project> findById(Long id);

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Project p WHERE p.deleteFlag = FALSE AND p.name = ?1")
    Boolean existsProjectName(String name);
}
