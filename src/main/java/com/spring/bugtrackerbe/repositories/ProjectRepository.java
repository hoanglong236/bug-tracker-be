package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Project p WHERE p.deleteFlag = FALSE")
    Page<Project> filterProjects(Pageable pageable);

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT p FROM Project p WHERE p.deleteFlag = FALSE AND p.id = ?1")
    @NonNull
    Optional<Project> findById(@NonNull Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Project p WHERE p.deleteFlag = FALSE AND p.name = ?1")
    Boolean existsProjectName(String name);
}
