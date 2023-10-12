package com.spring.bugtrackerbe.project.repositories;

import com.spring.bugtrackerbe.project.entities.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Integer> {

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT pr FROM ProjectRole pr WHERE pr.deleteFlag = FALSE")
    List<ProjectRole> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT pr FROM ProjectRole pr WHERE pr.deleteFlag = FALSE AND pr.id = ?1")
    Optional<ProjectRole> findById(Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(pr) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ProjectRole pr WHERE pr.deleteFlag = FALSE AND pr.name = ?1")
    Boolean existsProjectRoleName(String name);
}
