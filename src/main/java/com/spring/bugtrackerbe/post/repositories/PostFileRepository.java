package com.spring.bugtrackerbe.post.repositories;

import com.spring.bugtrackerbe.post.entities.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Integer> {
}
