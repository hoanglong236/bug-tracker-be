package com.spring.bugtrackerbe.project.repositories;

import com.spring.bugtrackerbe.project.entities.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.deleteFlag = FALSE AND pm.projectId = ?1")
    List<ProjectMember> findByProjectId(Long projectId);

    @Override
    @Transactional(readOnly = true)
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.deleteFlag = false AND pm.id = ?1")
    Optional<ProjectMember> findById(Long id);
}
