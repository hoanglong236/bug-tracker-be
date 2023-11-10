package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Override
    @Query("SELECT po FROM Post po WHERE po.deleteFlag = FALSE")
    @NonNull
    List<Post> findAll();


    @Transactional(readOnly = true)
    @Query("SELECT po FROM Post po WHERE po.deleteFlag = FALSE AND po.projectId = ?1")
    @NonNull
    List<Post> findByProjectId(int projectId);
}
